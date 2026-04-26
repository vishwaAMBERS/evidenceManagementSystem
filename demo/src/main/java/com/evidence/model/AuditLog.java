package com.evidence.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class AuditLog {

    @ManyToOne
    @JoinColumn(name = "officer_id")
    private User officer;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int logId;

    private int evidenceId;
    private int userId;
    private String action;

    private LocalDateTime timestamp;

    public void setEvidenceId(int evidenceId) {

    }

    public void setUserId(int userId) {

    }

    public void setAction(String action) {

    }

    public void setTimestamp(LocalDateTime now) {

    }

    // Getters & Setters
}