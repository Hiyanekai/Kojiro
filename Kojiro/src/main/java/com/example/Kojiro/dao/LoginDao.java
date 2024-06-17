package com.example.Kojiro.dao;

import com.example.Kojiro.entity.SignUp;
import com.example.Kojiro.entity.Users;
import com.example.Kojiro.entity.Users;

public interface LoginDao {
     Users findbylogin(String userId, String password);
     int signUp(SignUp signup);
     SignUp findByloginId(String user_id);
}
