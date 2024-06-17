package com.example.Kojiro.dao;

import com.example.Kojiro.entity.TermAddition;
import com.example.Kojiro.entity.TermManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PgTermManagementDao implements TermManagementDao{

    @Autowired
    public NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<TermManagement> findAll() {
        return jdbcTemplate.query("SELECT * FROM terms ORDER BY id",
                new DataClassRowMapper<>(TermManagement.class));
    }

    @Override
    public List<TermManagement> findByTerm(String key) {
        var param = new MapSqlParameterSource();
        param.addValue("key", "%" + key + "%");
        return jdbcTemplate.query("SELECT terms.id ,terms.term_name ,terms.explain,terms.file FROM terms WHERE terms.term_name like :key", param,
                new DataClassRowMapper<>(TermManagement.class));
    }

    @Override
    public int termAddition(TermAddition user){
        var param = new MapSqlParameterSource();
        param.addValue("term_name", user.term_name());
        param.addValue("explain", user.explain());
        param.addValue("file", user.file());
        return jdbcTemplate.update("INSERT INTO terms(term_name,explain,file) VALUES(:term_name,:explain,:file)", param);
    }

    @Override
    public TermAddition findtermAddition(String term_name) {
        var param = new MapSqlParameterSource();
        param.addValue("term_name", term_name);
        var list = jdbcTemplate.query("SELECT * FROM terms WHERE term_name=:term_name", param, new DataClassRowMapper<>(TermAddition.class));
        return list.isEmpty() ? null : list.get(0);
    }
}
