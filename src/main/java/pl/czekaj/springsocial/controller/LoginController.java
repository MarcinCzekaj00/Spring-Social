package pl.czekaj.springsocial.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pl.czekaj.springsocial.config.LoginCredentials;

@Controller
public class LoginController {

    @PostMapping("/login")
        public void login(@RequestBody LoginCredentials credentials) {
    }

}

