package com.example.Kojiro.service;

import com.example.Kojiro.dao.PgTermDao;
import com.example.Kojiro.dao.QManagementDao;
import com.example.Kojiro.entity.Term;
import com.example.Kojiro.entity.TestQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PgTermService implements TermService{

    @Autowired
    private PgTermDao termDao;

    @Override
    public List<Term> findAll() {
        return termDao.findAll();
    }
    @Override
    public List<Term> findByTerm(String term) {return termDao.findByTerm(term);}
}
