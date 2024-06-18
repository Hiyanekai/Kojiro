package com.example.Kojiro.service;

import com.example.Kojiro.dao.PgUserManagementDao;
import com.example.Kojiro.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PgUserManagementService implements UserManagementService {
    @Autowired
    private PgUserManagementDao PgUserManagementDao;

    @Override
    public List<UserManagement> findAll() {
        return PgUserManagementDao.findAll();
    }
    @Override
    public List<UserManagement> search(String user_id) {
        return PgUserManagementDao.search(user_id);
    }
    @Override
    public UserManagement findById(int id) {
        var detail =PgUserManagementDao.findById(id);
        return detail;
    }
    @Override
    public int delete(int id) {
        return PgUserManagementDao.delete(id);
    }

    @Override
    public int update(UserManagement change) {
        return PgUserManagementDao.update(change);
    }
    @Override
    public UserManagement findByUserId(String user_id) {
        var name =PgUserManagementDao.findByUserId(user_id);
        return name;
    }


}
