package com.example.Kojiro.dao;

import com.example.Kojiro.entity.questions;
import com.example.Kojiro.entity.testquestion;

import java.util.List;

public interface QuestionsDao {
    List<testquestion> findTest();

    questions findQuestion(int id);
}
