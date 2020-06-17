package klaicm.backlayer.tennisscores.controllers;

import klaicm.backlayer.tennisscores.model.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin()
@RestController
public class AuthController {

    @GetMapping(produces = "application/json")
    @RequestMapping({"/validateLogin"})
    public User validateLogin() {
        return new User("User successfully authenticated");
    }
}
