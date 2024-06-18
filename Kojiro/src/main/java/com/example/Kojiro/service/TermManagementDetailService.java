package com.example.Kojiro.service;

import com.example.Kojiro.entity.Genres;
import com.example.Kojiro.entity.TermManagementDetail;
import com.example.Kojiro.entity.TermManagementNotFile;

import java.util.List;

public interface TermManagementDetailService {

    TermManagementDetail findById(int id);
    int delete(int id);
//    List<Genres> findAll();
//    Genres findByGenres(int id);
    int update(TermManagementDetail change);
    int updates(TermManagementNotFile changes);
    TermManagementDetail findByTermId(String term_name);
    TermManagementDetail findByFile(String file);
}
