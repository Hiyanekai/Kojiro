package com.example.Kojiro.service;

import com.example.Kojiro.entity.Questions2points;
import com.example.Kojiro.entity.TestQuestion;
import com.example.Kojiro.entity.TestQuestionP2;
import com.example.Kojiro.entity.question;


import java.util.List;

public  interface QManagementService{

    public List<TestQuestion> findAll();
    List<TestQuestionP2> findAll2();
    public List<TestQuestion> findBySentence(String sentence);
    List<TestQuestionP2> findBySentence2(String sentence);
    public TestQuestion findById(int id);
    TestQuestionP2 findById2(int id);
    public int update(TestQuestion question);
    public int update(TestQuestionP2 question);
    public int delete(int id);
    public int delete2(int id);
    public int insert(TestQuestion question);
    int insert(Questions2points questions2points);
}
