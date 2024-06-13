package com.example.Kojiro.service;

import com.example.Kojiro.ProductNotFoundException;
import com.example.Kojiro.dao.PgQuestionsDao;
import com.example.Kojiro.entity.Questions;
import com.example.Kojiro.entity.Test_question;

import java.util.List;

public class PgQuestionsService implements QuestionsService{
    @Override
    public List<Test_question> findTest(){
        var queationdao = new PgQuestionsDao();
        var p = queationdao.findTest();
        System.out.println(p);
        return p;
    }
    @Override
    public Questions findQuestion(int id){
        var queationdao = new PgQuestionsDao();
        if (queationdao.findQuestion(id) == null)
            throw new ProductNotFoundException();
        else {
            return queationdao.findQuestion(id);
        }
    }

}
