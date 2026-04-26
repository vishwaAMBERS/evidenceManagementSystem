package com.evidence.service;

import com.evidence.model.AuditLog;
import com.evidence.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuditService {

    @Autowired
    private AuditLogRepository repo;

    public void logAction(int evidenceId, int userId, String action) {
        AuditLog log = new AuditLog();
        log.setEvidenceId(evidenceId);
        log.setUserId(userId);
        log.setAction(action);
        log.setTimestamp(LocalDateTime.now());

        repo.save(log);
    }
}