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
        return jdbcTemplate.query("SELECT * FROM terms ORDER BY id",
                new DataClassRowMapper<>(Term.class));
    }

    @Override
    public List<Term> findByTerm(String term) {
        var param = new MapSqlParameterSource();
        param.addValue("keyword","%"+term+"%");
        return jdbcTemplate.query("SELECT terms.id ,terms.term_name ,terms.explain,terms.file FROM terms WHERE terms.term_name like :keyword", param, new DataClassRowMapper<>(Term.class));
    }

    @Override
    public Term findById(int id) {
        var param = new MapSqlParameterSource();
        param.addValue("id", id);
        var list = jdbcTemplate.query("SELECT terms.id ,terms.term_name ,terms.explain,terms.file FROM terms WHERE id=:id", param, new DataClassRowMapper<>(Term.class));
        return list.isEmpty() ? null : list.get(0);
    }
}
