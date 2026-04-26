package com.evidence.controller;

import com.evidence.model.CaseN;
import com.evidence.service.CaseNService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/case")
public class CaseNController {

    @Autowired
    private CaseNService service;

    @PostMapping("/add")
    public CaseN addCase(@RequestBody CaseN c) {
        return service.saveCase(c);
    }

    @GetMapping("/all")
    public List<CaseN> getAll() {
        return service.getAllCases();
    }
}