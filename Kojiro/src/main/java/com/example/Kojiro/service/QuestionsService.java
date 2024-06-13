package com.example.Kojiro.service;

import com.example.Kojiro.entity.questions;
import com.example.Kojiro.entity.testquestion;

import java.util.List;

public interface QuestionsService {
    List<testquestion> findTest();

    questions findQuestion(int id);
}
