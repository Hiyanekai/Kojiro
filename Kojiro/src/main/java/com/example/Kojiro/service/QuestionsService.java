package com.example.Kojiro.service;

import com.example.Kojiro.entity.Questions;
import com.example.Kojiro.entity.TestQuestion;

import java.util.List;

public interface QuestionsService {
    List<TestQuestion> findTest();

    Questions findQuestion(int id);
}
