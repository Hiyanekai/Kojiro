package com.example.Kojiro.dao;

import com.example.Kojiro.entity.questions;
import com.example.Kojiro.entity.testquestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public class PgQuestionsDao implements QuestionsDao{
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Override
    public List<testquestion> findTest() {
        return jdbcTemplate.query("SELECT * FROM questions WHERE score = 1 ORDER BY RAND() LIMIT 10",
                new DataClassRowMapper<>(testquestion.class));
    }

    @Override
    public questions findQuestion(int id) {
        var param = new MapSqlParameterSource();
        param.addValue("id", id);
        var list = jdbcTemplate.query("SELECT * FROM questions WHERE id = :id", param, new DataClassRowMapper<>(questions.class));
        return list.isEmpty() ? null : list.get(0);
    }
}
