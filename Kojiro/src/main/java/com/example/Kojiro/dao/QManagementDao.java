package com.example.Kojiro.dao;

import com.example.Kojiro.entity.Questions2points;
import com.example.Kojiro.entity.TestQuestion;
import com.example.Kojiro.entity.TestQuestionP2;
import com.example.Kojiro.entity.question;


import java.util.List;

public interface QManagementDao {

    List<TestQuestion> findAll();
    List<TestQuestionP2> findAll2();
    List<TestQuestion> findBySentence(String sentence);
    List<TestQuestionP2> findBySentence2(String sentence);
    TestQuestion findById(int id);
    TestQuestionP2 findById2(int id);
    int update(TestQuestion question);
    int update(TestQuestionP2 question);
    int delete(int id);
    int delete2(int id);
    int insert(TestQuestion question);
    int insert(Questions2points questions2points);
}
