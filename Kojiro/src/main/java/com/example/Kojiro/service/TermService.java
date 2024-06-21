package com.example.Kojiro.service;

import com.example.Kojiro.entity.Term;
import com.example.Kojiro.entity.TestQuestion;

import java.util.List;

public interface TermService {

    public List<Term> findAll();
    public List<Term> findByTerm(String term);
    public Term findById(int id);
}
