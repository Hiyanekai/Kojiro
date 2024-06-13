package com.example.Kojiro.dao;

import com.example.Kojiro.entity.SignUp;
import com.example.Kojiro.entity.users;

public interface LoginDao {
     users findbylogin(String userId, String password);
     int signUp(SignUp signup);
     SignUp findByloginId(String user_id);
}
