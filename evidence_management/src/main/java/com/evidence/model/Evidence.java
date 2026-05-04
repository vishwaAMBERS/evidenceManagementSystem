package com.evidence.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "evidence") // Good practice to explicitly name your tables
public class Evidence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int evidenceId;

    private String filePath;
    private String fileType;
    private String hashValue;
    private LocalDateTime uploadDate;

    @ManyToOne
    @JoinColumn(name = "officer_id")
    private User officer;

    // --- CORRECTED GETTERS & SETTERS ---

    public int getEvidenceId() {
        return evidenceId; // Returns the actual ID
    }

    public void setEvidenceId(int evidenceId) {
        this.evidenceId = evidenceId;
    }

    public String getFilePath() {
        return filePath; // Fixed compilation error
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath; // Actually saves the data now
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType; // Actually saves the data now
    }

    public String getHashValue() {
        return hashValue; // Returns the actual string, not null
    }

    public void setHashValue(String hashValue) {
        this.hashValue = hashValue; // Actually saves the data now
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    public User getOfficer() {
        return officer;
    }

    public void setOfficer(User officer) {
        this.officer = officer;
    }
}