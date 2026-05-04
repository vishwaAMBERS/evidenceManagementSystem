package com.evidence.service;

import com.evidence.model.AuditLog;
import com.evidence.model.Evidence;
import com.evidence.model.User;
import com.evidence.repository.AuditLogRepository;
import com.evidence.repository.EvidenceRepository;
import com.evidence.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuditService {

    @Autowired
    private AuditLogRepository auditRepo;

    @Autowired
    private EvidenceRepository evidenceRepo;

    @Autowired
    private UserRepository userRepo;

    // ✅ Correct logging method
    public void logAction(int evidenceId, int userId, String action) {

        // 🔹 Fetch user
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🔹 Fetch evidence
        Evidence evidence = evidenceRepo.findById(evidenceId)
                .orElseThrow(() -> new RuntimeException("Evidence not found"));

        // 🔹 Create log
        AuditLog log = new AuditLog();
        log.setAction(action);
        log.setTimestamp(LocalDateTime.now());

        // ✅ Set relationships (IMPORTANT FIX)
        log.setOfficer(user);
        log.setEvidence(evidence);

        // 🔹 Save
        auditRepo.save(log);
    }
}