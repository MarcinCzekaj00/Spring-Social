package pl.czekaj.springsocial.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.czekaj.springsocial.dto.UserDto;
import pl.czekaj.springsocial.model.User;
import pl.czekaj.springsocial.service.UserService;

import java.util.List;

import static pl.czekaj.springsocial.controller.controllerHelper.PageAndSortDirectionHelper.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers(@RequestParam(required = false) Integer page, Sort.Direction sort){
        int pageNumber = getPageNumberGreaterThenZeroAndNotNull(page);
        Sort.Direction sortDirection = getSortDirectionNotNullAndASC(sort);
        List<UserDto> details = userService.getUsers(pageNumber,sortDirection);
        return new ResponseEntity<>(details, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id){
        UserDto user = userService.getSingleUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/{id}/info")
    public ResponseEntity<User> getUserInfo(@PathVariable Long id){
        User user = userService.getUserInfo(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user){
        return new ResponseEntity<>(userService.addUser(user), HttpStatus.OK);
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
