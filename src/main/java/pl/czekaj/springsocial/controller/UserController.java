package pl.czekaj.springsocial.controller;

import javassist.runtime.Desc;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.czekaj.springsocial.controller.controllerHelper.PageHelper;
import pl.czekaj.springsocial.controller.controllerHelper.SortDirectionHelper;
import pl.czekaj.springsocial.dto.UserDto;
import pl.czekaj.springsocial.model.Post;
import pl.czekaj.springsocial.model.Relationship;
import pl.czekaj.springsocial.model.User;
import pl.czekaj.springsocial.service.UserService;

import java.util.List;
import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<CollectionModel<UserDto>> getUsers(@RequestParam(required = false) Integer page,
                                                             @RequestParam(required = false, defaultValue = "ASC") Sort.Direction sort){
        int pageNumber = PageHelper.getPageNumberGreaterThenZeroAndNotNull(page);
        Sort.Direction sortDirection = SortDirectionHelper.getSortDirection(sort);
        List<UserDto> users = userService.getUsers(pageNumber,sortDirection);
        for(UserDto user: users){
            Set<Post> posts = user.getPosts();
            Set<Relationship> relationships = user.getFriends();
            for(Relationship relationship: relationships){
                user.add(linkTo(methodOn(UserController.class).getUser(relationship.getToUserId())).withRel("Friend"));
            }
            for(Post post: posts){
                user.add(linkTo(methodOn(PostController.class).getSinglePost(post.getPostId())).withRel("Post"));
            }
            user.add(linkTo(methodOn(UserController.class).getUser(user.getUserId())).withSelfRel());
        }
        Link link = linkTo(methodOn(UserController.class).getUsers(pageNumber,sortDirection)).withSelfRel();
        CollectionModel<UserDto> userResource = new CollectionModel<>(users,link);
        return new ResponseEntity<>(userResource, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UserDto>> getUser(@PathVariable Long id){
        UserDto user = userService.getSingleUser(id);
        Set<Post> posts = user.getPosts();
        Set<Relationship> relationships = user.getFriends();
        for(Relationship relationship: relationships){
            user.add(linkTo(methodOn(UserController.class).getUser(relationship.getToUserId())).withRel("Friend"));
        }
        for(Post post: posts){
            user.add(linkTo(methodOn(PostController.class).getSinglePost(post.getPostId())).withRel("Post"));
        }
        Link link = linkTo(methodOn(UserController.class).getUser(user.getUserId())).withSelfRel();
        EntityModel<UserDto> userResource = new EntityModel<>(user,link);
        return new ResponseEntity<>(userResource, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}/info")
    public ResponseEntity<EntityModel<User>> getUserInfo(@PathVariable Long id){
        User user = userService.getUserInfo(id);
        if(user.getDetails()!=null){
            user.add(linkTo(methodOn(UserDetailsController.class).getDetails(user.getUserId())).withRel("Details"));
        }
        Set<Post> posts = user.getPosts();
        Set<Relationship> relationships = user.getFriends();
        for(Relationship relationship: relationships){
            user.add(linkTo(methodOn(UserController.class).getUser(relationship.getToUserId())).withRel("Friend"));
        }
        for(Post post: posts){
            user.add(linkTo(methodOn(PostController.class).getSinglePost(post.getPostId())).withRel("Post"));
        }
        Link link = linkTo(methodOn(UserController.class).getUser(user.getUserId())).withSelfRel();
        EntityModel<User> userResource = new EntityModel<>(user,link);
        return new ResponseEntity<>(userResource, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user){
        return new ResponseEntity<>(userService.addUser(user), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UserDto> editUser(@RequestBody User user){
        return new ResponseEntity<>(userService.editUser(user), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> editSingleUser(@RequestBody User user, @PathVariable Long id){
        return new ResponseEntity<>(userService.editSingleUser(user,id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }
}
