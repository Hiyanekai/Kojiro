package com.example.Kojiro.service;

import com.example.Kojiro.entity.TestQuestion;
import com.example.Kojiro.entity.question;


import java.util.List;

public  interface QManagementService{

    public List<TestQuestion> findAll();
    public List<TestQuestion> findBySentence(String sentence);
    public TestQuestion findById(int id);
    public int update(TestQuestion question);
    public int delete(int id);
    public int insert(TestQuestion question);
}
