package com.example.Kojiro.service;

import com.example.Kojiro.dao.PgTermManagementDao;
import com.example.Kojiro.entity.Genres;
import com.example.Kojiro.entity.TermManagement;
import com.example.Kojiro.entity.TermManagementNotFile;
import com.example.Kojiro.entity.UserManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PgTermManagementService implements TermManagementService {
    @Autowired
    private PgTermManagementDao PgTermManagementDao;

    @Override
    public TermManagement findById(int id) {
        var detail =PgTermManagementDao.findById(id);
        return detail;
    }
    @Override
    public int delete(int id) {
        return PgTermManagementDao.delete(id);
    }
    @Override
    public List<Genres> findAll() {
        return PgTermManagementDao.findAll();
    }
    @Override
    public Genres findByGenres(int id) {
        var genres = PgTermManagementDao.findByGenres(id);
        return genres;
    }
    @Override
    public int update(TermManagement change) {
        return PgTermManagementDao.update(change);
    }
    @Override
    public int updates(TermManagementNotFile changes) {
        return PgTermManagementDao.updates(changes);
    }
    @Override
    public TermManagement findByTermId(String term_name) {
        var name =PgTermManagementDao.findByTermId(term_name);
        return name;
    }
    @Override
    public TermManagement findByFile(String file) {
        var name =PgTermManagementDao.findByFile(file);
        return name;
    }
}
