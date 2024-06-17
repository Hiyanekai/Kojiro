package com.example.Kojiro.dao;

import com.example.Kojiro.entity.TermAddition;
import com.example.Kojiro.entity.TermManagement;

import java.util.List;

public interface TermManagementDao {
    List<TermManagement> findAll();
    List<TermManagement> findByTerm(String key);
    int termAddition(TermAddition user);
    TermAddition findtermAddition(String term_name);
}
