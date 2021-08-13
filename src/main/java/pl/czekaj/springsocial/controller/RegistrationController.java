package pl.czekaj.springsocial.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.czekaj.springsocial.model.User;
import pl.czekaj.springsocial.service.UserService;

import javax.validation.Valid;

@AllArgsConstructor
@Controller
@RequestMapping("/register")
public class RegistrationController {

    private UserService userService;

    @GetMapping
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "registerForm";
    }

    @PostMapping
    public String addUser(@ModelAttribute @Valid User user,
                          BindingResult bindResult) {
        if(bindResult.hasErrors())
            return "registerForm";
        else {
            userService.addWithDefaultRole(user);
            return "registerSuccess";
        }
    }
}
