package com.evidence.service;

import com.evidence.model.User;
import com.evidence.repository.UserRepository;
import com.evidence.util.PasswordUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    // ✅ Secure login
    public User login(String username, String password) {

        if (username == null || password == null) {
            throw new RuntimeException("Username or password cannot be null");
        }

        User user = repo.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // 🔐 Hash input password
        String hashedInput = PasswordUtil.hashPassword(password);

        // 🔐 Compare hashed passwords
        if (user.getPassword().equals(hashedInput)) {
            return user;
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

    // ✅ Register user (NEW METHOD)
    public User register(User user) {

        if (repo.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("Username already exists");
        }

        // 🔐 Hash password before saving
        user.setPassword(PasswordUtil.hashPassword(user.getPassword()));

        return repo.save(user);
    }
}