package com.example.Kojiro.dao;

import com.example.Kojiro.entity.*;

import java.util.List;

public interface UserManagementDao {
    List<UserManagement> findAll();
    UserManagement findById(int id);
    int update (UserManagement change);
}
