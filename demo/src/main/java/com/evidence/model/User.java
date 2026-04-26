package com.evidence.model;

import jakarta.persistence.*;

@Entity
public class User {

    @ManyToOne
    @JoinColumn(name = "officer_id")
    private User officer;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    private String name;
    private String username;
    private String password;
    private String role;

    public Object getPassword() {
            return null;
    }

    public String getRole() {
            return null;
    }

    // Getters & Setters
}