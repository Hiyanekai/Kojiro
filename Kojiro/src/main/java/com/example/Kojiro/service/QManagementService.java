package com.example.Kojiro.service;

import com.example.Kojiro.entity.question;
import com.example.Kojiro.entity.questions;
import com.example.Kojiro.entity.testquestion;

import java.util.List;

public  interface QManagementService{

    public List<testquestion> findAll();
    public List<testquestion> findBySentence(String sentence);
}
