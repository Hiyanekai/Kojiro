package com.example.Kojiro.service;

import com.example.Kojiro.entity.*;

import java.util.List;

public interface MyPageService {
    public Users findById(int id);
    public List<Scores> findAll();
    public List<Scores> findMe(int userId);
    public List<Weakness> WeaknessFindMe(int userId);
    public List<Concern> ConcernFindMe(int userId);
}
