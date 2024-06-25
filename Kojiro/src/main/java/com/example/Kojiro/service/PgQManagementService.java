package com.example.Kojiro.service;

import com.example.Kojiro.dao.QManagementDao;
import com.example.Kojiro.entity.Questions2points;
import com.example.Kojiro.entity.TestQuestion;
import com.example.Kojiro.entity.TestQuestionP2;
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
    public List<TestQuestionP2> findAll2() {
        return qManagementDao.findAll2();
    }

    @Override
    public List<TestQuestion> findBySentence(String sentence) {return qManagementDao.findBySentence(sentence);}

    @Override
    public List<TestQuestionP2> findBySentence2(String sentence) {
        return qManagementDao.findBySentence2(sentence);
    }

    @Override
    public TestQuestion findById(int id) {
        return qManagementDao.findById(id);
    }

    @Override
    public TestQuestionP2 findById2(int id) {
        return qManagementDao.findById2(id);
    }

    @Override
    public int update(TestQuestion question){
        return qManagementDao.update(question);
    }

    @Override
    public int update(TestQuestionP2 question) {
        return qManagementDao.update(question);
    }

    @Override
    public int delete(int id){
        return qManagementDao.delete(id);
    }

    @Override
    public int delete2(int id) {
        return qManagementDao.delete2(id);
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
