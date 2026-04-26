package com.evidence.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CaseN {

    @ManyToOne
    @JoinColumn(name = "officer_id")
    private User officer;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int caseId;

    private String caseName;
    private String description;

    @Column(name = "officer_id", insertable = false, updatable = false)
    private int officerId;

    // Getters & Setters
}