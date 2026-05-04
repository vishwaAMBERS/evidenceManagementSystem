package com.evidence.model;

import jakarta.persistence.*;
import lombok.Data;

@Data // Lombok handles all getters, setters, equals, hashCode, and toString!
@Entity
@Table(name = "cases") // Renamed to "cases" for cleaner database naming
public class CaseN {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int caseId;

    private String caseName;
    private String description;

    @ManyToOne
    @JoinColumn(name = "officer_id")
    private User officer;

}