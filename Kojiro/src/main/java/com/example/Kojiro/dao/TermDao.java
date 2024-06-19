package com.example.Kojiro.dao;

import com.example.Kojiro.entity.Term;
import com.example.Kojiro.entity.TestQuestion;

import java.util.List;

public interface TermDao {

    List<Term> findAll();
    List<Term> findByTerm(String term);
}
