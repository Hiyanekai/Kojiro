package com.example.Kojiro.service;

import com.example.Kojiro.dao.MyPageDao;
import com.example.Kojiro.entity.Scores;
import com.example.Kojiro.entity.SignUp;
import com.example.Kojiro.entity.Users;
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
    public List<Scores> findAll() {
        return myPageDao.findAll();
    }

    @Override
    public List<Scores> findMe(int userId){
        return myPageDao.findMe(userId);
    }
}