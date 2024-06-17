package com.example.Kojiro.service;

import com.example.Kojiro.ProductNotFoundException;
import com.example.Kojiro.dao.PgQuestionsDao;
import com.example.Kojiro.entity.Questions;
import com.example.Kojiro.entity.TestQuestion;
import com.example.Kojiro.entity.TestResults;
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
    public List<TestResults> findTestResult(){
        return iQuestionDao.findTestResult();
    }

    @Override
    public Questions findQuestion(int id){
        if (iQuestionDao.findQuestion(id) == null)
            throw new ProductNotFoundException();
        else {
            return iQuestionDao.findQuestion(id);
        }
    }

}
