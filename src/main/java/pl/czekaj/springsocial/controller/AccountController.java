package pl.czekaj.springsocial.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.czekaj.springsocial.model.Post;
import pl.czekaj.springsocial.model.User;
import pl.czekaj.springsocial.repository.UserRepository;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RequiredArgsConstructor
@RestController
public class AccountController {

    private final UserRepository userRepository;

    @GetMapping("/account")
    public ResponseEntity<EntityModel<User>> viewUserAccountInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user1 = userRepository.findByEmail(authentication.getName());
        for(Post post:user1.getPosts()){
            user1.add(linkTo(methodOn(PostController.class).getSinglePost(post.getPostId())).withRel("Post"));
        }
        user1.add(linkTo(methodOn(UserDetailsController.class).getDetails(user1.getUserId())).withRel("Details"));
        Link link = linkTo(methodOn(AccountController.class).viewUserAccountInfo()).withSelfRel();
        EntityModel<User> userResource = new EntityModel<>(user1,link);
        return new ResponseEntity<>(userResource, HttpStatus.OK);
    }
}
