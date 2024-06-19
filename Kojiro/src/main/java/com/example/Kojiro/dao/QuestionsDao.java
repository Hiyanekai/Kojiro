package com.example.Kojiro.dao;

import com.example.Kojiro.entity.Questions;
import com.example.Kojiro.entity.TestQuestion;
import com.example.Kojiro.entity.TestResults;

import java.util.List;

public interface QuestionsDao {
    List<TestQuestion> findTest();

    List<TestResults> findTestResult();

    Questions findQuestion(int id);
}
