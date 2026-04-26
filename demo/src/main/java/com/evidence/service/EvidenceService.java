package com.evidence.service;

import com.evidence.model.Evidence;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class EvidenceService {
    public List<Evidence> getAllEvidence() {

        return null;
    }

    public Evidence getById(int id) {

        return null;
    }



    public String generateHash(byte[] bytes) {
            return null;
    }

    public void saveEvidence(Evidence e) {

    }

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
