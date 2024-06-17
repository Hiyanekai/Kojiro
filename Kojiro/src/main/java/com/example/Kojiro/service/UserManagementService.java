package com.example.Kojiro.service;

import com.example.Kojiro.entity.*;

import java.util.List;

public interface UserManagementService {
    List<UserManagement> findAll();
    UserManagement findById(int id);
    int update(UserManagement change);
}
