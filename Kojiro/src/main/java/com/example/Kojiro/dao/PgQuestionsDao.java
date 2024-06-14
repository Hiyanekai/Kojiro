package com.example.Kojiro.dao;

import com.example.Kojiro.entity.Questions;
import com.example.Kojiro.entity.TestQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public class PgQuestionsDao implements QuestionsDao{
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Override
    public List<TestQuestion> findTest() {
        return jdbcTemplate.query("SELECT q.id,g.genre_name,q.sentence,q.answer,q.explain,q.file,q.score FROM questions AS q " +
                                       "JOIN genres AS g ON question.genre_id = genres.id WHERE score = 1 ORDER BY RAND() LIMIT 10",
                new DataClassRowMapper<>(TestQuestion.class));
    }

    @Override
    public Questions findQuestion(int id) {
        var param = new MapSqlParameterSource();
        param.addValue("id", id);
        var list = jdbcTemplate.query("SELECT * FROM questions WHERE id = :id", param, new DataClassRowMapper<>(Questions.class));
        return list.isEmpty() ? null : list.get(0);
    }
}
