package com.example.Kojiro.service;

import com.example.Kojiro.entity.TermAddition;
import com.example.Kojiro.entity.TermManagement;

import java.util.List;

public interface TermManagementService {
    List<TermManagement> findAll();
    List<TermManagement> findByTerm(String key);
    int termAddition(TermAddition user);
    TermAddition findtermAddition(String term_name);
}
