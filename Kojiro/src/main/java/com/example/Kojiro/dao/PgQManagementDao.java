package com.example.Kojiro.dao;

import com.example.Kojiro.entity.question;
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
    public List<question> findAll() {
        return jdbcTemplate.query("SELECT questions.id, questions.sentence, questions.answer, questions.explain, genres.genre_name FROM questions INNER JOIN genres ON questions.genre_id = genres.id ORDER BY questions.id",
                new DataClassRowMapper<>(question.class));
    }

    @Override
    public List<question> findBySentence(String sentence) {
        var param = new MapSqlParameterSource();
        param.addValue("keyword","%"+sentence+"%");
        return jdbcTemplate.query("SELECT questions.id, questions.sentence, questions.answer, questions.explain, genres.genre_name FROM questions INNER JOIN genres ON questions.genre_id = genres.id WHERE questions.sentence like :keyword ORDER BY questions.id", param, new DataClassRowMapper<>(question.class));
    }
}
