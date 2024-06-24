package com.example.Kojiro.dao;

import com.example.Kojiro.entity.*;

import java.util.List;

public interface MyPageDao {
    public Users findById(int id);
    public List<Scores> findAll();
    public List<Scores> findMe(int userId);
    public Scores findByIdForScores(int userId,int scoreId);
    public List<Weakness> WeaknessFindMe(int userId);
    public List<Concern> ConcernFindMe(int userId);
    public List<ScoreDetail> ScoreDetailFindMe(int userId, int scoreId);
}
