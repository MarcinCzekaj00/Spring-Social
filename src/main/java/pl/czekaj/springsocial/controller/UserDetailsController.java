package pl.czekaj.springsocial.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.czekaj.springsocial.dto.UserDetailsDto;
import pl.czekaj.springsocial.model.UserDetails;
import pl.czekaj.springsocial.service.UserDetailsService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RestController
public class UserDetailsController {

    private final UserDetailsService userDetailsService;


    /* Method returning all details
       Uncomment it and getDetails in UserDetailsService if needed


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/details")
    public ResponseEntity<List<UserDetailsDto>> getDetails(@RequestParam(required = false) Integer page, Sort.Direction sort){
        int pageNumber = getPageNumberGreaterThenZeroAndNotNull(page);
        Sort.Direction sortDirection = getSortDirectionNotNullAndDESC(sort);
        List<UserDetailsDto> details = userDetailsService.getDetails(pageNumber,sortDirection);
        return new ResponseEntity<>(details, HttpStatus.OK);
    }*/

    @GetMapping("/users/{userId}/details")
    public ResponseEntity<EntityModel<UserDetailsDto>> getDetails(@PathVariable Long userId){
        UserDetailsDto details = userDetailsService.getSingleDetails(userId);
        details.add(linkTo(UserController.class).slash(userId).withRel("User"));
        Link link = linkTo(methodOn(UserDetailsController.class).getDetails(userId)).withSelfRel();
        EntityModel<UserDetailsDto> userDetailsDtoResource = new EntityModel<>(details,link);
        return new ResponseEntity<>(userDetailsDtoResource, HttpStatus.OK);
    }

    @PostMapping(value = "/users/{userId}/details")
    public ResponseEntity<UserDetailsDto> addDetails(@RequestBody UserDetails details, @PathVariable Long userId){
        return new ResponseEntity<>(userDetailsService.addDetails(details,userId), HttpStatus.CREATED);
    }

    @PutMapping(value = "/users/{userId}/details")
    public ResponseEntity<UserDetailsDto> editDetails(@RequestBody UserDetails details, @PathVariable Long userId){
        return new ResponseEntity<>(userDetailsService.editDetails(details,userId), HttpStatus.OK);
    }

    @DeleteMapping("/users/{userId}/details")
    public void deletePost(@PathVariable Long userId){
        userDetailsService.deleteDetails(userId);
    }

}