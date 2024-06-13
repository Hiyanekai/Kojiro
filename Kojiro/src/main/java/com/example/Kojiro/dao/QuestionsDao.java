package com.example.Kojiro.dao;

import com.example.Kojiro.entity.Questions;
import com.example.Kojiro.entity.Test_question;

import java.util.List;

public interface QuestionsDao {
    List<Test_question> findTest();

    Questions findQuestion(int id);
}
