package com.evidence.service;

import com.evidence.model.CaseN;
import com.evidence.repository.CaseNRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaseNService {

    @Autowired
    private CaseNRepository repo;

    public CaseN saveCase(CaseN c) {
        return repo.save(c);
    }

    public List<CaseN> getAllCases() {
        return repo.findAll();
    }
}