package com.example.Kojiro.service;

import com.example.Kojiro.entity.Questions;
import com.example.Kojiro.entity.Test_question;

import java.util.List;

public interface QuestionsService {
    List<Test_question> findTest();

    Questions findQuestion(int id);
}
