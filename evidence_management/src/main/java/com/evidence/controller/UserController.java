package com.evidence.controller;

import com.evidence.model.User;
import com.evidence.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    // ✅ LOGIN
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password) {

        User user = service.login(username, password);

        return "Login successful ✔ Role: " + user.getRole();
    }

    // ✅ REGISTER (NEW)
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        service.register(user);
        return "User registered successfully ✔";
    }
}