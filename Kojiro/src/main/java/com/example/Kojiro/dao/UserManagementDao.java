package com.example.Kojiro.dao;

import com.example.Kojiro.entity.*;

import java.util.List;

public interface UserManagementDao {
    List<UserManagement> findAll();
    List<UserManagement> search(String user_id);
    UserManagement findById(int id);
    int delete(int id);
    int update (UserManagement change);
    UserManagement findByUserId(String user_id);
}
