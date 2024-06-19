package com.example.Kojiro.dao;

import com.example.Kojiro.entity.Genres;

import java.util.List;

public interface GenresDao {
    List<Genres> findAll();
    List<Genres> findByStep(int step);
}
