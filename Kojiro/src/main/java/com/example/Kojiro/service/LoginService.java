package com.example.Kojiro.service;


import com.example.Kojiro.entity.SignUp;
import com.example.Kojiro.entity.users;

public interface LoginService {
    users findbylogin(String userId, String password);
    int signUp(SignUp signup);
    SignUp findByloginId(String user_id);
}
