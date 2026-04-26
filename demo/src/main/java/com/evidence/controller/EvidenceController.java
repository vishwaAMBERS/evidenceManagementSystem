package com.evidence.controller;

import com.evidence.model.Evidence;
import com.evidence.service.EvidenceService;
import com.evidence.service.AuditService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.File;
import java.util.List;



@RestController
@RequestMapping("/evidence")
public class EvidenceController {

    @Autowired
    private EvidenceService evidenceService;

    @Autowired
    private AuditService auditService;

    // 🔹 Upload Evidence
    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file,
                         @RequestParam("userId") int userId,
                         @RequestParam("caseId") int caseId) {
        try {
            // Generate hash
            String uploadDir = "uploads/";
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Save file
            String filePath = uploadDir + file.getOriginalFilename();
            File dest = new File(filePath);
            file.transferTo(dest);


            String hash = evidenceService.generateHash(file.getBytes());

            // Save file (simple path example)

            // Create entity
            Evidence e = new Evidence();
            e.setFilePath(filePath);
            e.setFileType(file.getContentType());
            e.setHashValue(hash);

            evidenceService.saveEvidence(e);

            // Log action
            auditService.logAction(e.getEvidenceId(), 1, "UPLOAD");

            return "File uploaded successfully";

        } catch (Exception ex) {
            return "Error uploading file";
        }
    }

    // 🔹 Get All Evidence
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable int id) {

        try {
            Evidence evidence = evidenceService.getById(id);

            Path path = Paths.get(evidence.getFilePath());
            Resource resource = new UrlResource(path.toUri());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + path.getFileName() + "\"")
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/verify/{id}")
    public String verify(@PathVariable int id) {

        Evidence evidence = evidenceService.getById(id);

        boolean isValid = evidenceService.verifyFileIntegrity(evidence);

        if (isValid) {
            return "File is NOT tampered ✔";
        } else {
            return "File is TAMPERED ❌";
        }
    }
    @GetMapping("/all")
    public List<Evidence> getAll() {
        return evidenceService.getAllEvidence();
    }
}