package com.example.Kojiro.service;

import com.example.Kojiro.entity.Genres;

import java.util.List;

public interface GenresService {
    List<Genres> findAll();
    List<Genres> findByStep(int step);
}
