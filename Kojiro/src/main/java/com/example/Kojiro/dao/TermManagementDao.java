package com.example.Kojiro.dao;

import com.example.Kojiro.entity.Genres;
import com.example.Kojiro.entity.TermManagement;
import com.example.Kojiro.entity.TermManagementNotFile;

import java.util.List;

public interface TermManagementDao {
    TermManagement findById(int id);
    int delete(int id);
    List<Genres> findAll();
    Genres findByGenres(int id);
    int update (TermManagement change);
    int updates (TermManagementNotFile changes);
    TermManagement findByTermId(String term_name);
    TermManagement findByFile(String file);
}
