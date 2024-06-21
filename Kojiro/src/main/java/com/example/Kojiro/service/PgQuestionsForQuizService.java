package com.example.Kojiro.service;

import com.example.Kojiro.dao.PgQuestionsForQuizDao;
import com.example.Kojiro.entity.Questions;
import com.example.Kojiro.entity.Questions2points;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PgQuestionsForQuizService {
    @Autowired
    private PgQuestionsForQuizDao pgQuestionsForQuizDao;

    public List<Questions2points> findByGenre(int genreId){
        return pgQuestionsForQuizDao.findByGenre(genreId);
    }

    public List<Questions2points> findRandom(){
        return pgQuestionsForQuizDao.findRandom();
    }

    public Questions2points findById(int id){
        return pgQuestionsForQuizDao.findById(id);
    }

    public List<Questions2points> quizGetBy2points(int genreId){
        return pgQuestionsForQuizDao.quizGetBy2points(genreId);
    }

    public List<Questions2points> findAllForFlag(int u_id){
        return pgQuestionsForQuizDao.findAllForFlag(u_id);
    }

    public int delFlagQuestion(int id, int gId){
        return pgQuestionsForQuizDao.delFlagQuestion(id, gId);
    }
}
