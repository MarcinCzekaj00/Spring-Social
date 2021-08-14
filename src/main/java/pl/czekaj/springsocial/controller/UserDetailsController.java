package pl.czekaj.springsocial.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.czekaj.springsocial.dto.PostDto;
import pl.czekaj.springsocial.dto.UserDetailsDto;
import pl.czekaj.springsocial.model.UserDetails;
import pl.czekaj.springsocial.service.UserDetailsService;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static pl.czekaj.springsocial.controller.controllerHelper.PageAndSortDirectionHelper.getPageNumberGreaterThenZeroAndNotNull;
import static pl.czekaj.springsocial.controller.controllerHelper.PageAndSortDirectionHelper.getSortDirectionNotNullAndDESC;

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

    @GetMapping("/users/{id}/details")
    public ResponseEntity<EntityModel<UserDetailsDto>> getDetails(@PathVariable Long id){
        UserDetailsDto details = userDetailsService.getSingleDetails(id);
        details.add(linkTo(UserController.class).slash(id).withRel("User"));
        Link link = linkTo(methodOn(UserDetailsController.class).getDetails(id)).withSelfRel();
        EntityModel<UserDetailsDto> userDetailsDtoResource = new EntityModel<>(details,link);
        return new ResponseEntity<>(userDetailsDtoResource, HttpStatus.OK);
    }

    @PostMapping(value = "/users/{id}/details")
    public ResponseEntity<UserDetailsDto> addDetails(@RequestBody UserDetails details, @PathVariable Long id){
        return new ResponseEntity<>(userDetailsService.addDetails(details,id), HttpStatus.OK);
    }

    @PutMapping(value = "/users/{id}/details")
    public ResponseEntity<UserDetailsDto> editDetails(@RequestBody UserDetails details, @PathVariable Long id){
        return new ResponseEntity<>(userDetailsService.editDetails(details,id), HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}/details")
    public void deletePost(@PathVariable Long id){
        userDetailsService.deleteDetails(id);
    }

}