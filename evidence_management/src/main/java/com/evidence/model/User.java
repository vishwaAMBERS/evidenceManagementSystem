package com.evidence.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users") // Prevents SQL errors if 'user' is a reserved keyword
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    private String name;
    private String username;
    private String password;
    private String role;

    @ManyToOne
    @JoinColumn(name = "officer_id")
    private User officer;

    // --- CORRECTED GETTERS & SETTERS ---

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password; // Now returns the actual string, not null!
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role; // Now returns the actual string, not null!
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User getOfficer() {
        return officer;
    }

    public void setOfficer(User officer) {
        this.officer = officer;
    }
}