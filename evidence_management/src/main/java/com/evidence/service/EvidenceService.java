package com.evidence.service;

import com.evidence.model.Evidence;
import com.evidence.repository.EvidenceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.List;
import java.util.Optional;

@Service
public class EvidenceService {

    @Autowired
    private EvidenceRepository repo;

    // ✅ Get all evidence
    public List<Evidence> getAllEvidence() {
        return repo.findAll();
    }

    // ✅ Get evidence by ID
    public Evidence getById(int id) {
        Optional<Evidence> optional = repo.findById(id);
        return optional.orElseThrow(() -> new RuntimeException("Evidence not found with id: " + id));
    }

    // ✅ Save evidence
    public void saveEvidence(Evidence e) {
        repo.save(e);
    }

    // ✅ Generate SHA-256 hash
    public String generateHash(byte[] data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(data);

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();

        } catch (Exception e) {
            throw new RuntimeException("Error generating hash");
        }
    }

    // ✅ Verify file integrity
    public boolean verifyFileIntegrity(Evidence evidence) {
        try {
            Path path = Paths.get(evidence.getFilePath());
            byte[] fileData = Files.readAllBytes(path);

            String newHash = generateHash(fileData);

            return newHash.equals(evidence.getHashValue());

        } catch (Exception e) {
            throw new RuntimeException("Error verifying file");
        }
    }
}