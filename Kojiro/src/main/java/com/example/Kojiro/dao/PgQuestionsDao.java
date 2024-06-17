package com.example.Kojiro.dao;

import com.example.Kojiro.entity.Questions;
import com.example.Kojiro.entity.TestQuestion;
import com.example.Kojiro.entity.TestResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PgQuestionsDao implements QuestionsDao{
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Override
    public List<TestQuestion> findTest() {
        return jdbcTemplate.query("SELECT q.id,g.genre_name AS genre,q.sentence,q.answer,q.explain,q.file,q.score FROM questions AS q " +
                                       "JOIN genres AS g ON q.genre_id = g.id WHERE score = 1 ORDER BY random() LIMIT 90",
                new DataClassRowMapper<>(TestQuestion.class));
    }

    @Override
    public List<TestResults> findTestResult() {
        return jdbcTemplate.query("SELECT * from test_result",
                new DataClassRowMapper<>(TestResults.class));
    }

    @Override
    public Questions findQuestion(int id) {
        var param = new MapSqlParameterSource();
        param.addValue("id", id);
        var list = jdbcTemplate.query("SELECT * FROM questions WHERE id = :id", param, new DataClassRowMapper<>(Questions.class));
        return list.isEmpty() ? null : list.get(0);
    }
}
