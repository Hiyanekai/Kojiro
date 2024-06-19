package com.example.Kojiro.service;

import com.example.Kojiro.dao.PgQuestionsForQuizDao;
import com.example.Kojiro.entity.Questions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PgQuestionsForQuizService {
    @Autowired
    private PgQuestionsForQuizDao pgQuestionsForQuizDao;

    public List<Questions> findByGenre(int genreId){
        return pgQuestionsForQuizDao.findByGenre(genreId);
    }

    public List<Questions> findRandom(){
        return pgQuestionsForQuizDao.findRandom();
    }
}
