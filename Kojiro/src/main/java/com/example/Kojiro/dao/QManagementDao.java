package com.example.Kojiro.dao;

import com.example.Kojiro.entity.question;
import com.example.Kojiro.entity.questions;
import com.example.Kojiro.entity.testquestion;

import java.util.List;

public interface QManagementDao {

    List<testquestion> findAll();
    List<testquestion> findBySentence(String sentence);
}
