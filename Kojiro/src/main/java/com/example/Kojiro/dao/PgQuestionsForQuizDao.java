package com.example.Kojiro.dao;

import com.example.Kojiro.entity.Questions;
import com.example.Kojiro.entity.Questions2points;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PgQuestionsForQuizDao {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public List<Questions2points> findByGenre(int genreId){
        var param = new MapSqlParameterSource();
        param.addValue("genre_id", genreId);
        return jdbcTemplate.query("SELECT * FROM questions WHERE genre_id = :genre_id ORDER BY random() LIMIT 10", param,
                new DataClassRowMapper<>(Questions2points.class));
    }

    public List<Questions2points> quizGetBy2points(int genreId){
        var param = new MapSqlParameterSource();
        param.addValue("genre_id", genreId);
        return jdbcTemplate.query("SELECT * FROM questions_2points WHERE genre_id = :genre_id ORDER BY random() LIMIT 10", param,
                new DataClassRowMapper<>(Questions2points.class));
    }

    public List<Questions2points> findRandom(){
        return jdbcTemplate.query("SELECT * FROM questions ORDER BY random() LIMIT 10",
                new DataClassRowMapper<>(Questions2points.class));
    }

    public Questions2points findById(int id){
        var param = new MapSqlParameterSource();
        param.addValue("id", id);
        var result =  jdbcTemplate.query("SELECT * FROM questions WHERE id = :id", param,
                new DataClassRowMapper<>(Questions2points.class));
        return !result.isEmpty()? result.get(0):null;
    }

    public List<Questions2points> findAllForFlag(int u_id){
        var param = new MapSqlParameterSource();
        param.addValue("u_id", u_id);
        var questions = jdbcTemplate.query("SELECT * FROM questions WHERE id IN (SELECT q_id FROM flags WHERE q_id_2points = 0 AND user_id = :u_id GROUP BY q_id)", param,
                new DataClassRowMapper<>(Questions2points.class));
        var questions2points = jdbcTemplate.query("SELECT * FROM questions_2points WHERE id IN (SELECT q_id_2points FROM flags WHERE q_id = 0 AND user_id = :u_id GROUP BY q_id_2points)", param,
                new DataClassRowMapper<>(Questions2points.class));
        if(questions.isEmpty() && !questions2points.isEmpty()) return questions2points;
        if(!questions.isEmpty() && questions2points.isEmpty()) return questions;
        if(questions.isEmpty() && questions2points.isEmpty()) return null;

        questions.addAll(questions2points);

        return questions;
    }
    public List<Questions2points> findAllForMiss(int u_id){
        var param = new MapSqlParameterSource();
        param.addValue("u_id", u_id);
        var questions = jdbcTemplate.query("SELECT * FROM questions WHERE id IN (SELECT q_id FROM miss WHERE q_id_2points = 0 AND user_id = :u_id GROUP BY q_id)", param,
                new DataClassRowMapper<>(Questions2points.class));
        var questions2points = jdbcTemplate.query("SELECT * FROM questions_2points WHERE id IN (SELECT q_id_2points FROM miss WHERE q_id = 0 AND user_id = :u_id GROUP BY q_id_2points)", param,
                new DataClassRowMapper<>(Questions2points.class));
        if(questions.isEmpty() && !questions2points.isEmpty()) return questions2points;
        if(!questions.isEmpty() && questions2points.isEmpty()) return questions;
        if(questions.isEmpty() && questions2points.isEmpty()) return null;

        questions.addAll(questions2points);

        return questions;
    }

    public int delFlagQuestion(int id, int gId){
        var param = new MapSqlParameterSource();
        if(gId!=30){
            param.addValue("id", id);
            return jdbcTemplate.update("DELETE FROM flags WHERE q_id = :id", param);
        } else {
            param.addValue("id", id);
            return jdbcTemplate.update("DELETE FROM flags WHERE q_id_2points = :id", param);
        }
    }
    public int delMissQuestion(int id, int gId){
        var param = new MapSqlParameterSource();
        if(gId!=30){
            param.addValue("id", id);
            return jdbcTemplate.update("DELETE FROM miss WHERE q_id = :id", param);
        } else {
            param.addValue("id", id);
            return jdbcTemplate.update("DELETE FROM miss WHERE q_id_2points = :id", param);
        }
    }


}
