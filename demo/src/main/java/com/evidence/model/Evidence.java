package com.evidence.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Evidence {

    @ManyToOne
    @JoinColumn(name = "officer_id")
    private User officer;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int evidenceId;

    private String filePath;
    private String fileType;
    private String hashValue;

    private LocalDateTime uploadDate;

    public String getFilePath() {
                return null;
    }

    public void setFilePath(String filePath) {
    }

    public void setFileType(String contentType) {
    }

    public void setHashValue(String hash) {
    }

    public int getEvidenceId() {

        return 0;
    }

    public String getHashValue() {
                return null;
    }

    // Getters & Setters
}