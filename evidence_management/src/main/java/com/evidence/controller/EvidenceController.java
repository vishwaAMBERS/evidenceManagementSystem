package com.evidence.controller;

import com.evidence.model.Evidence;
import com.evidence.model.User;
import com.evidence.service.EvidenceService;
import com.evidence.service.AuditService;
import com.evidence.repository.UserRepository;

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
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/evidence")
public class EvidenceController {

    @Autowired
    private EvidenceService evidenceService;

    @Autowired
    private AuditService auditService;

    @Autowired
    private UserRepository userRepo;

    // ✅ Upload Evidence (FIXED)
    @PostMapping("/upload")
    public ResponseEntity<String> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") int userId) {

        try {
            // 🔹 Fetch user
            User user = userRepo.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // 🔹 Create upload directory
            String uploadDir = "uploads/";
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // ✅ Generate unique filename (FIXED)
            String uniqueName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            String filePath = uploadDir + uniqueName;

            // 🔹 Save file
            File dest = new File(filePath);
            file.transferTo(dest);

            // 🔹 Generate hash
            String hash = evidenceService.generateHash(file.getBytes());

            // 🔹 Create entity
            Evidence e = new Evidence();
            e.setFilePath(filePath);
            e.setFileType(file.getContentType());
            e.setHashValue(hash);
            e.setOfficer(user);
            e.setUploadDate(java.time.LocalDateTime.now());

            // 🔹 Save to DB
            evidenceService.saveEvidence(e);

            // 🔹 Audit log
            auditService.logAction(e.getEvidenceId(), userId, "UPLOAD");

            return ResponseEntity.ok("✅ File uploaded successfully");

        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("❌ Error uploading file: " + ex.getMessage());
        }
    }

    // ✅ Download Evidence
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

    // ✅ Verify Evidence Integrity
    @GetMapping("/verify/{id}")
    public ResponseEntity<String> verify(@PathVariable int id) {

        try {
            Evidence evidence = evidenceService.getById(id);

            boolean isValid = evidenceService.verifyFileIntegrity(evidence);

            if (isValid) {
                return ResponseEntity.ok("✔ File is NOT tampered");
            } else {
                return ResponseEntity.ok("❌ File is TAMPERED");
            }

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error verifying file");
        }
    }

    // ✅ Get All Evidence
    @GetMapping("/all")
    public List<Evidence> getAll() {
        return evidenceService.getAllEvidence();
    }
}