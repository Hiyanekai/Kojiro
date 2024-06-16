package com.example.Kojiro.service;

import com.example.Kojiro.dao.PgTermManagementDao;
import com.example.Kojiro.entity.TermManagement;
import com.example.Kojiro.entity.UserManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PgTermManagementService implements TermManagementService {
    @Autowired
    private PgTermManagementDao PgTermManagementDao;

    @Override
    public TermManagement findById(int id) {
        var detail =PgTermManagementDao.findById(id);
        return detail;
    }
}
