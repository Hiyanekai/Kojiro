package com.example.Kojiro.service;

import com.example.Kojiro.entity.question;

import java.util.List;

public  interface QManagementService{

    public List<question> findAll();
    public List<question> findBySentence(String sentence);
}
