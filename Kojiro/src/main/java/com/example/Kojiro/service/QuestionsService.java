package com.example.Kojiro.service;

import com.example.Kojiro.entity.*;

import java.util.List;

public interface QuestionsService {
    List<TestQuestion> findTest();

    List<TestQuestion> findTestP2();

    List<TestResults> findTestResult(int userId);

    Questions findQuestion(int id);

    QuestionsP2 findQuestionP2(int id);

    int insertMiss(Misses miss);
}
