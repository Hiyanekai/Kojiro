package com.example.Kojiro.dao;

import com.example.Kojiro.entity.*;
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
    public List<TestQuestion> findTestP2() {
        return jdbcTemplate.query("SELECT q.id,g.genre_name AS genre,q.sentence,q.answer,q.explain,q.file,q.score FROM questions_2points AS q " +
                        "JOIN genres AS g ON q.genre_id = g.id WHERE score = 2 ORDER BY random() LIMIT 5",
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
        var list = jdbcTemplate.query("SELECT * FROM questions_2points WHERE id = :id", param, new DataClassRowMapper<>(QuestionsP2.class));
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public int insertMiss(Misses miss){
        var param = new MapSqlParameterSource();
        param.addValue("user_id", miss.user_ID());
        param.addValue("q_id", miss.q_id());
        param.addValue("q_id_2points", miss.q_id_2points());
        return jdbcTemplate.update("INSERT INTO miss(user_id,q_id,q_id_2points) VALUES(:user_id,:q_id,:q_id_2points)", param);
    }

    @Override
    public int insertFlags(Flags flags){
        var param = new MapSqlParameterSource();
        param.addValue("user_id", flags.user_ID());
        param.addValue("q_id", flags.q_id());
        param.addValue("q_id_2points", flags.q_id_2points());
        return jdbcTemplate.update("INSERT INTO flags(q_id,q_id_2points,user_id) VALUES(:q_id,:q_id_2points,:user_id)", param);
    }

    @Override
    public int insertScores(Scores scores){
        var param = new MapSqlParameterSource();
        param.addValue("user_id", scores.user_id());
        param.addValue("score", scores.score());
        param.addValue("test_date", scores.test_date());
        return jdbcTemplate.update("INSERT INTO scores(user_id,score,test_date) VALUES(:user_id,:score,:test_date)", param);
    }

    @Override
    public int insertTestResults(TestResults results){
        var param = new MapSqlParameterSource();
        param.addValue("q_id", results.q_id());
        param.addValue("user_select", Integer.parseInt(results.user_select()));
        param.addValue("score_id", results.score_id());
        param.addValue("score", results.score());
        param.addValue("flag", results.flag());
        param.addValue("user_id", results.user_id());
        return jdbcTemplate.update("INSERT INTO test_results(q_id,user_select,score_id,score,flag,user_id) VALUES(:q_id,:user_select,:score_id,:score,:flag,:user_id)", param);
    }

    @Override
    public int insertTestResultsP2(TestResults results){
        var param = new MapSqlParameterSource();
        param.addValue("q_id", results.q_id());
        param.addValue("user_select", results.user_select());
        param.addValue("score_id", results.score_id());
        param.addValue("score", results.score());
        param.addValue("flag", results.flag());
        param.addValue("user_id", results.user_id());
        return jdbcTemplate.update("INSERT INTO test_results_2points(q_id,user_select,score_id,score,flag,user_id) VALUES(:q_id,:user_select,:score_id,:score,:flag,:user_id)", param);
    }
}
