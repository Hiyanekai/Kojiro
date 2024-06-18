package com.example.Kojiro.service;

import com.example.Kojiro.entity.Genres;
import com.example.Kojiro.entity.TermManagementDetail;
import com.example.Kojiro.entity.TermManagementNotFile;
import com.example.Kojiro.entity.TermAddition;

import java.util.List;

public interface TermManagementDetailService {

    TermManagementDetail findById(int id);
    int delete(int id);
    List<Genres> findAll();
    Genres findByGenres(int id);
    int update(TermManagementDetail change);
    int updates(TermManagementNotFile changes);
    TermManagementDetail findByTermId(String term_name);
    TermManagementDetail findByFile(String file);
//    List<TermManagementDetail> findAll();
//    List<TermManagementDetail> findByTerm(String key);
//    int termAddition(TermAddition user);
//    TermAddition findtermAddition(String term_name);
}
