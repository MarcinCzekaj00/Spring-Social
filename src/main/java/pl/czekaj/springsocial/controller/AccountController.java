package pl.czekaj.springsocial.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.czekaj.springsocial.model.User;
import pl.czekaj.springsocial.repository.UserRepository;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
public class AccountController {

    private final UserRepository userRepository;

    @GetMapping("/account")
    public ResponseEntity<User> viewUserAccountInfo(Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        return new ResponseEntity<>(user, HttpStatus.OK);

    }
}
