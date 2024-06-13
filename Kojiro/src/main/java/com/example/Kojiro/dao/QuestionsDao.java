package com.example.Kojiro.dao;

import com.example.Kojiro.entity.Questions;

import java.util.List;

public interface QuestionsDao {
    List<Questions> findTest();

    Questions findQuestion(int id);
}
