package com.example.Kojiro.dao;

import com.example.Kojiro.entity.TestQuestion;
import com.example.Kojiro.entity.question;


import java.util.List;

public interface QManagementDao {

    List<TestQuestion> findAll();
    List<TestQuestion> findBySentence(String sentence);
    TestQuestion findById(int id);
}
