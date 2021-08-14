package pl.czekaj.springsocial.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.czekaj.springsocial.model.User;
import pl.czekaj.springsocial.service.RelationshipService;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static pl.czekaj.springsocial.controller.controllerHelper.PageAndSortDirectionHelper.getPageNumberGreaterThenZeroAndNotNull;
import static pl.czekaj.springsocial.controller.controllerHelper.PageAndSortDirectionHelper.getSortDirectionNotNullAndDESC;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/{id}/friends")
public class RelationshipController {

    private final RelationshipService relationshipService;

    @GetMapping
    public ResponseEntity<List<Long>> getFriends (@PathVariable Long id, @RequestParam(required = false) Integer page, Sort.Direction sort){
        int pageNumber = getPageNumberGreaterThenZeroAndNotNull(page);
        Sort.Direction sortDirection = getSortDirectionNotNullAndDESC(sort);
        List<Long> ids = relationshipService.getFriends(id,pageNumber,sortDirection);
        return new ResponseEntity<>(ids, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> addFriend(@PathVariable Long id, @RequestBody User userToAdd){
        return new ResponseEntity<>(relationshipService.addFriend(id,userToAdd),HttpStatus.CREATED);
    }

    @DeleteMapping
    public void deleteFriend(@PathVariable Long id, @RequestBody User userToDelete){
        relationshipService.deleteFriend(id,userToDelete);
    }

}
