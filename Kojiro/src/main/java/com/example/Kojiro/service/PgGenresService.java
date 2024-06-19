package com.example.Kojiro.service;

import com.example.Kojiro.dao.GenresDao;
import com.example.Kojiro.entity.Genres;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PgGenresService implements GenresService {
    @Autowired
    private GenresDao genresDao;
    @Override
    public List<Genres> findAll() {
        return genresDao.findAll();
    }

    @Override
    public List<Genres> findByStep(int step) {
        return genresDao.findByStep(step);
    }
}
