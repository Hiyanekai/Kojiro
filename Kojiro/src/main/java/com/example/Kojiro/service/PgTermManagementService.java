package com.example.Kojiro.service;

import com.example.Kojiro.dao.TermManagementDao;
import com.example.Kojiro.entity.TermAddition;
import com.example.Kojiro.entity.TermManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PgTermManagementService implements TermManagementService{

    @Autowired
    private TermManagementDao termManagementDao;

    @Override
    public List<TermManagement> findAll() {
        return termManagementDao.findAll();
    }

    @Override
    public List<TermManagement> findByTerm(String key){
        return termManagementDao.findByTerm(key);
    }

    @Override
    public int termAddition(TermAddition user) {
        return termManagementDao.termAddition(user);
    }
    @Override
    public TermAddition findtermAddition(String term_name){
        return termManagementDao.findtermAddition(term_name);
    }
}
