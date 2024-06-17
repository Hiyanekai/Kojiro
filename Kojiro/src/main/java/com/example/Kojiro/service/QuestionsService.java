package com.example.Kojiro.service;

import com.example.Kojiro.entity.Questions;
import com.example.Kojiro.entity.TestQuestion;
import com.example.Kojiro.entity.TestResults;

import java.util.List;

public interface QuestionsService {
    List<TestQuestion> findTest();

    List<TestResults> findTestResult();

    Questions findQuestion(int id);
}
