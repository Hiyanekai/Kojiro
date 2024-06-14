package com.example.Kojiro.dao;

import com.example.Kojiro.entity.TestQuestion;
import com.example.Kojiro.entity.question;


import java.util.List;

public interface QManagementDao {

    List<TestQuestion> findAll();
    List<TestQuestion> findBySentence(String sentence);
    TestQuestion findById(int id);
    int update(TestQuestion question);
    int delete(int id);
    int insert(TestQuestion question);
}
