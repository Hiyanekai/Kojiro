package com.example.Kojiro.dao;

import com.example.Kojiro.entity.question;
import com.example.Kojiro.entity.questions;
import com.example.Kojiro.entity.testquestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PgQManagementDao implements QManagementDao{

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<testquestion> findAll() {
        return jdbcTemplate.query("SELECT questions.id, genres.genre_name AS genre, questions.sentence, questions.answer, questions.explain FROM questions INNER JOIN genres ON questions.genre_id = genres.id ORDER BY questions.id",
                new DataClassRowMapper<>(testquestion.class));
    }

    @Override
    public List<testquestion> findBySentence(String sentence) {
        var param = new MapSqlParameterSource();
        param.addValue("keyword","%"+sentence+"%");
        return jdbcTemplate.query("SELECT questions.id, genres.genre_name AS genre, questions.sentence, questions.answer, questions.explainFROM questions INNER JOIN genres ON questions.genre_id = genres.id WHERE questions.sentence like :keyword ORDER BY questions.id", param, new DataClassRowMapper<>(testquestion.class));
    }
}
