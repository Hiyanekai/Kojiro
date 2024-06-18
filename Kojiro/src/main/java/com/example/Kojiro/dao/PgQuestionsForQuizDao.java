package com.example.Kojiro.dao;

import com.example.Kojiro.entity.Questions;
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

    public List<Questions> findByGenre(int genreId){
        var param = new MapSqlParameterSource();
        param.addValue("genre_id", genreId);
        return jdbcTemplate.query("SELECT * FROM questions WHERE genre_id = :genre_id ORDER BY random() LIMIT 10", param,
                new DataClassRowMapper<>(Questions.class));
    }

    public List<Questions> findRandom(){
        return jdbcTemplate.query("SELECT * FROM questions ORDER BY random() LIMIT 10",
                new DataClassRowMapper<>(Questions.class));
    }

    public Questions findById(int id){
        var param = new MapSqlParameterSource();
        param.addValue("id", id);
        var result =  jdbcTemplate.query("SELECT * FROM questions WHERE id = :id", param,
                new DataClassRowMapper<>(Questions.class));
        return result!=null? result.get(0):null;
    }

}
