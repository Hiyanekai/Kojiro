package com.example.Kojiro.dao;

import com.example.Kojiro.entity.*;

import java.util.List;

public interface MyPageDao {
    public Users findById(int id);
    public List<Scores> findAll();
    public List<Scores> findMe(int userId);
    Scores findByIdForScores(int id);
    public List<Weakness> WeaknessFindMe(int userId);
    public List<Concern> ConcernFindMe(int userId);
    public List<ScoreDetail> ScoreDetailFindMe(int userId, int scoreId);
}
