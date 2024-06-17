package com.example.Kojiro.dao;

import com.example.Kojiro.entity.TestQuestion;
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
    public List<TestQuestion> findAll() {
        return jdbcTemplate.query("SELECT questions.id, genres.genre_name AS genre, questions.sentence, questions.answer, questions.explain, questions.file, questions.score FROM questions INNER JOIN genres ON questions.genre_id = genres.id ORDER BY questions.id",
                new DataClassRowMapper<>(TestQuestion.class));
    }

    @Override
    public List<TestQuestion> findBySentence(String sentence) {
        var param = new MapSqlParameterSource();
        param.addValue("keyword","%"+sentence+"%");
        return jdbcTemplate.query("SELECT questions.id, genres.genre_name AS genre, questions.sentence, questions.answer, questions.explain, questions.file, questions.score  FROM questions INNER JOIN genres ON questions.genre_id = genres.id WHERE questions.sentence like :keyword ORDER BY questions.id", param, new DataClassRowMapper<>(TestQuestion.class));
    }

    @Override
    public TestQuestion findById(int id) {
        var param = new MapSqlParameterSource();
        param.addValue("id", id);
        var list = jdbcTemplate.query("SELECT questions.id, genres.genre_name AS genre, questions.sentence, questions.answer, questions.explain, questions.file, questions.score FROM questions INNER JOIN genres ON questions.genre_id = genres.id WHERE questions.id = :id ORDER BY questions.id", param, new DataClassRowMapper<>(TestQuestion.class));
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public int update(TestQuestion question){
        var param = new MapSqlParameterSource();
        param.addValue("id",question.id());
        param.addValue("genre",Integer.parseInt(question.genre()));
        param.addValue("sentence", question.sentence());
        param.addValue("answer", question.answer());
        param.addValue("explain", question.explain());
        param.addValue("file", question.file());
        param.addValue("score", question.score());
        return jdbcTemplate.update("UPDATE questions SET genre_id = :genre, sentence = :sentence, answer = :answer, explain = :explain, file = :file, score = :score WHERE id = :id", param);
    }
}
