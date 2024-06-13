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
    public UserManagement findById(int id) {
        var detail =PgUserManagementDao.findById(id);
        return detail;
    }

    @Override
    public int update(UserManagement change) {
        return PgUserManagementDao.update(change);
    }

}
