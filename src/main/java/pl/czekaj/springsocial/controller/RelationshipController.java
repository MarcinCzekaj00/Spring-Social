package pl.czekaj.springsocial.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.czekaj.springsocial.controller.controllerHelper.PageHelper;
import pl.czekaj.springsocial.controller.controllerHelper.SortDirectionHelper;
import pl.czekaj.springsocial.dto.PostDto;
import pl.czekaj.springsocial.dto.mapper.SimpleUserDtoMapper;
import pl.czekaj.springsocial.dto.SimpleUserDto;
import pl.czekaj.springsocial.model.User;
import pl.czekaj.springsocial.repository.UserRepository;
import pl.czekaj.springsocial.service.RelationshipService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/{userId}/friends")
public class RelationshipController {

    private final RelationshipService relationshipService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<CollectionModel<SimpleUserDto>> getFriends (@PathVariable Long userId,
                                                                      @RequestParam(required = false) Integer page,
                                                                      @RequestParam(required = false, defaultValue = "DESC") Sort.Direction sort){
        int pageNumber = PageHelper.getPageNumberGreaterThenZeroAndNotNull(page);
        Sort.Direction sortDirection = SortDirectionHelper.getSortDirection(sort);
        List<Long> ids = relationshipService.getFriends(userId,pageNumber,sortDirection);
        List<SimpleUserDto> users = new ArrayList<>();
        for(Long userIds:ids){
            users.add(SimpleUserDtoMapper.mapTosimpleUserDtos(userRepository.getById(userIds)));
        }
        for(SimpleUserDto user: users){
            user.add(linkTo(UserController.class).slash(user.getUserId()).withRel("User"));
        }
        Link link = linkTo(methodOn(RelationshipController.class).getFriends(userId,pageNumber,sortDirection)).withSelfRel();
        CollectionModel<SimpleUserDto> simpleUserDtoResource = new CollectionModel<>(users,link);
        return new ResponseEntity<>(simpleUserDtoResource, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> addFriend(@PathVariable Long userId, @RequestBody User userToAdd){
        return new ResponseEntity<>(relationshipService.addFriend(userId,userToAdd),HttpStatus.CREATED);
    }

    @DeleteMapping
    public void deleteFriend(@PathVariable Long userId, @RequestBody User userToDelete){
        relationshipService.deleteFriend(userId,userToDelete);
    }

}
