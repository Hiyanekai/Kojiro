package com.example.Kojiro.dao;

import com.example.Kojiro.entity.Scores;
import com.example.Kojiro.entity.Users;
import com.example.Kojiro.entity.Weakness;

import java.util.List;

public interface MyPageDao {
    public Users findById(int id);
    public List<Scores> findAll();
    public List<Scores> findMe(int userId);
    public List<Weakness> WeaknessFindMe(int userId);
}
