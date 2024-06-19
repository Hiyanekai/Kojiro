package com.example.Kojiro.dao;

import com.example.Kojiro.entity.Term;
import com.example.Kojiro.entity.TestQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PgTermDao implements TermDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Term> findAll() {
        return jdbcTemplate.query("SELECT questions.id, genres.genre_name AS genre, questions.sentence, questions.answer, questions.explain, questions.file, questions.score FROM questions INNER JOIN genres ON questions.genre_id = genres.id ORDER BY questions.id",
                new DataClassRowMapper<>(Term.class));
    }

    @Override
    public List<Term> findByTerm(String term) {
        var param = new MapSqlParameterSource();
        param.addValue("keyword","%"+term+"%");
        return jdbcTemplate.query("SELECT questions.id, genres.genre_name AS genre, questions.sentence, questions.answer, questions.explain, questions.file, questions.score  FROM questions INNER JOIN genres ON questions.genre_id = genres.id WHERE questions.sentence like :keyword ORDER BY questions.id", param, new DataClassRowMapper<>(Term.class));
    }
}
