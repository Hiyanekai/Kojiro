package com.example.Kojiro.service;

import com.example.Kojiro.dao.PgTermManagementDetailDao;
import com.example.Kojiro.entity.Genres;
import com.example.Kojiro.entity.TermManagementDetail;
import com.example.Kojiro.entity.TermManagementNotFile;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.Kojiro.dao.TermManagementDetailDao;
import com.example.Kojiro.entity.TermAddition;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PgTermManagementDetailService implements TermManagementDetailService {
    @Autowired
    private PgTermManagementDetailDao PgTermManagementDao;

    @Override
    public TermManagementDetail findById(int id) {
        var detail = PgTermManagementDao.findById(id);
        return detail;
    }

    @Override
    public int delete(int id) {
        return PgTermManagementDao.delete(id);
    }

//    @Override
//    public List<Genres> findAll() {
//        return PgTermManagementDao.findAll();
//    }

//    @Override
//    public Genres findByGenres(int id) {
//        var genres = PgTermManagementDao.findByGenres(id);
//        return genres;
//    }

    @Override
    public int update(TermManagementDetail change) {
        return PgTermManagementDao.update(change);
    }

    @Override
    public int updates(TermManagementNotFile changes) {
        return PgTermManagementDao.updates(changes);
    }

    @Override
    public TermManagementDetail findByTermId(String term_name) {
        var name = PgTermManagementDao.findByTermId(term_name);
        return name;
    }

    @Override
    public TermManagementDetail findByFile(String file) {
        var name = PgTermManagementDao.findByFile(file);
        return name;
    }
}
