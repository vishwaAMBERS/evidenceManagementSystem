package com.evidence.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data // Let Lombok handle all getters and setters automatically!
@Entity
@Table(name = "audit_logs") // Good practice for clean database naming
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int logId;

    private String action;
    private LocalDateTime timestamp;

    // Link directly to the User (Officer) who performed the action
    @ManyToOne
    @JoinColumn(name = "officer_id")
    private User officer;

    // Link directly to the Evidence this action was performed on
    @ManyToOne
    @JoinColumn(name = "evidence_id")
    private Evidence evidence;

}