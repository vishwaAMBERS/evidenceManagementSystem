package com.evidence.controller;

import com.evidence.model.User;
import com.evidence.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password) {

        User user = service.login(username, password);

        if (user != null) {
            return "Login successful ✔ Role: " + user.getRole();
        } else {
            return "Invalid credentials ❌";
        }
    }
}