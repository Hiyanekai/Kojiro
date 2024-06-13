package com.example.Kojiro.service;

import com.example.Kojiro.dao.LoginDao;
import com.example.Kojiro.entity.SignUp;
import com.example.Kojiro.entity.users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PgLoginService implements LoginService{
    @Autowired
    private LoginDao loginDao;

    @Override
    public users findbylogin(String userId, String password){
        return loginDao.findbylogin(userId,password);
    }

    @Override
    public int signUp(SignUp signup){
        return loginDao.signUp(signup);
    }

    @Override
    public SignUp findByloginId(String user_id){
        return loginDao.findByloginId(user_id);
    }
}
