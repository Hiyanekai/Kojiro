package com.example.Kojiro.dao;

import com.example.Kojiro.entity.Questions;
import com.example.Kojiro.entity.TestQuestion;
import com.example.Kojiro.entity.TestResults;

import java.util.List;

public interface QuestionsDao {
    List<TestQuestion> findTest();
    List<TestQuestion> findTestP2();

    List<TestResults> findTestResult(int userId);

    Questions findQuestion(int id);
}
