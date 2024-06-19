package com.example.Kojiro.dao;

import com.example.Kojiro.entity.Genres;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PgGenresDao implements GenresDao{

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Genres> findAll() {
        return jdbcTemplate.query("SELECT * FROM genres ORDER BY id",
                new DataClassRowMapper<>(Genres.class));
    }

    @Override
    public List<Genres> findByStep(int step) {
        var param = new MapSqlParameterSource();
        param.addValue("step", step);
        return jdbcTemplate.query("SELECT * FROM genres WHERE step = :step ORDER BY id", param,
                new DataClassRowMapper<>(Genres.class));
    }
}
