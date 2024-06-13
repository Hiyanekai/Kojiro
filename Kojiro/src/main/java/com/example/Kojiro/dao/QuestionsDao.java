package com.example.Kojiro.dao;

import com.example.Kojiro.entity.Questions;
import com.example.Kojiro.entity.TestQuestion;

import java.util.List;

public interface QuestionsDao {
    List<TestQuestion> findTest();

    Questions findQuestion(int id);
}
