package com.example.Kojiro.service;

import com.example.Kojiro.dao.QManagementDao;
import com.example.Kojiro.entity.Questions2points;
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
    @Override
    public int update(TestQuestion question){
        return qManagementDao.update(question);
    }
    @Override
    public int delete(int id){
        return qManagementDao.delete(id);
    }
    @Override
    public int insert(TestQuestion question) {
        return qManagementDao.insert(question);
    }

    @Override
    public int insert(Questions2points questions2points) {
        return qManagementDao.insert(questions2points);
    }
}
