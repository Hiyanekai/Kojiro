package com.example.Kojiro.dao;

import com.example.Kojiro.entity.question;

import java.util.List;

public interface QManagementDao {

    List<question> findAll();
    List<question> findBySentence(String sentence);
}
