package com.example.Kojiro.service;

import com.example.Kojiro.dao.QManagementDao;
import com.example.Kojiro.entity.TestQuestion;
import com.example.Kojiro.entity.question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PgQManagementService implements QManagementService{

    @Autowired
    private QManagementDao qManagementDao;
    @Override
    public List<TestQuestion> findAll() {
        return qManagementDao.findAll();
    }
    @Override
    public List<TestQuestion> findBySentence(String sentence) {return qManagementDao.findBySentence(sentence);}
    @Override
    public TestQuestion findById(int id) {
        return qManagementDao.findById(id);
    }
}
