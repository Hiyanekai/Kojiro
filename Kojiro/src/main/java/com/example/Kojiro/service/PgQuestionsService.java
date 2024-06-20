package com.example.Kojiro.service;

import com.example.Kojiro.ProductNotFoundException;
import com.example.Kojiro.dao.PgQuestionsDao;
import com.example.Kojiro.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PgQuestionsService implements QuestionsService{

    @Autowired
    PgQuestionsDao iQuestionDao;
    @Override
    public List<TestQuestion> findTest(){
       return iQuestionDao.findTest();
    }

    @Override
    public List<TestQuestion> findTestP2(){
        return iQuestionDao.findTestP2();
    }

    @Override
    public List<TestResults> findTestResult(int userId){
        return iQuestionDao.findTestResult(userId);
    }

    @Override
    public Questions findQuestion(int id){
        if (iQuestionDao.findQuestion(id) == null)
            throw new ProductNotFoundException();
        else {
            return iQuestionDao.findQuestion(id);
        }
    }

    @Override
    public QuestionsP2 findQuestionP2(int id){
        if (iQuestionDao.findQuestion(id) == null)
            throw new ProductNotFoundException();
        else {
            return iQuestionDao.findQuestionP2(id);
        }
    }

    @Override
    public int insertMiss(Misses miss){
        return iQuestionDao.insertMiss(miss);
    }

}
