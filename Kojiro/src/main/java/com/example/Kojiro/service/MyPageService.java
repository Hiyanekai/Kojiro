package com.example.Kojiro.service;

import com.example.Kojiro.entity.*;

import java.util.List;

public interface MyPageService {
    public Users findById(int id);
    public Scores findByIdForScores(int userId,int scoreId);
    public List<Scores> findAll();
    public List<Scores> findMe(int userId);
    public List<Weakness> WeaknessFindMe(int userId);
    public List<Concern> ConcernFindMe(int userId);
    public List<ScoreDetail> ScoreDetailFindMe(int userId,int scoreId);
}
