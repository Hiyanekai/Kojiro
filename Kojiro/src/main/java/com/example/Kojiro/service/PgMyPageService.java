package com.example.Kojiro.service;

import com.example.Kojiro.dao.MyPageDao;
import com.example.Kojiro.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PgMyPageService implements MyPageService {
    @Autowired
    MyPageDao myPageDao;
    @Override
    public Users findById(int id) {
        return myPageDao.findById(id);
    }

    @Override
    public Scores findByIdForScores(int userId,int scoreId) {
        return myPageDao.findByIdForScores(userId,scoreId);
    }

    @Override
    public List<Scores> findAll() {
        return myPageDao.findAll();
    }

    @Override
    public List<Scores> findMe(int userId){
        return myPageDao.findMe(userId);
    }

    @Override
    public List<Weakness> WeaknessFindMe(int userId){
        return myPageDao.WeaknessFindMe(userId);
    }
    @Override
    public List<Concern> ConcernFindMe(int userId){return myPageDao.ConcernFindMe(userId);}

    @Override
    public List<ScoreDetail> ScoreDetailFindMe(int userId,int scoreId){return myPageDao.ScoreDetailFindMe(userId, scoreId);}
}
