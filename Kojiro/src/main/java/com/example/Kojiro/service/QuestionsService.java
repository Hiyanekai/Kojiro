package com.example.Kojiro.service;

import com.example.Kojiro.entity.Questions;

import java.util.List;

public interface QuestionsService {
    List<Questions> findTest();

    Questions findQuestion(int id);
}
