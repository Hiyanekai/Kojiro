package com.example.Kojiro.dao;

import com.example.Kojiro.entity.Questions;
import com.example.Kojiro.entity.TestQuestion;
import com.example.Kojiro.entity.QuestionsP2;
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
                                       "JOIN genres AS g ON q.genre_id = g.id WHERE score = 1 ORDER BY random() LIMIT 10",
                new DataClassRowMapper<>(TestQuestion.class));
    }

    @Override
    public List<TestQuestion> findTestP2() {
        return jdbcTemplate.query("SELECT q.id,g.genre_name AS genre,q.sentence,q.answer,q.explain,q.file,q.score FROM questions_2points AS q " +
                        "JOIN genres AS g ON q.genre_id = g.id WHERE score = 1 ORDER BY random() LIMIT 5",
                new DataClassRowMapper<>(TestQuestion.class));
    }

    @Override
    public List<TestResults> findTestResult(int userId) {
        var param = new MapSqlParameterSource();
        param.addValue("user_id", userId);
        return jdbcTemplate.query("SELECT * FROM test_results WHERE user_id = :user_id", param, new DataClassRowMapper<>(TestResults.class));
    }

    @Override
    public Questions findQuestion(int id) {
        var param = new MapSqlParameterSource();
        param.addValue("id", id);
        var list = jdbcTemplate.query("SELECT * FROM questions WHERE id = :id", param, new DataClassRowMapper<>(Questions.class));
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public QuestionsP2 findQuestionP2(int id) {
        var param = new MapSqlParameterSource();
        param.addValue("id", id);
        var list = jdbcTemplate.query("SELECT * FROM questions WHERE id = :id", param, new DataClassRowMapper<>(QuestionsP2.class));
        return list.isEmpty() ? null : list.get(0);
    }
}
