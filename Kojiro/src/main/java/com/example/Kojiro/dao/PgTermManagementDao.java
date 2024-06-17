package com.example.Kojiro.dao;

import com.example.Kojiro.entity.TermManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PgTermManagementDao implements TermManagementDao{
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Override
    public TermManagement findById(int id) {
        var param = new MapSqlParameterSource();
        param.addValue("id", id);
        var list = jdbcTemplate.query("SELECT * FROM terms WHERE id = :id ORDER BY id" , param, new DataClassRowMapper<>(TermManagement.class));
        return list.isEmpty() ? null : list.get(0);
    }
}
