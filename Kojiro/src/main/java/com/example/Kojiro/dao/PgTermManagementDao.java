package com.example.Kojiro.dao;

import com.example.Kojiro.entity.Genres;
import com.example.Kojiro.entity.TermManagement;
import com.example.Kojiro.entity.TermManagementNotFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    @Override
    public int delete(int id){
        var param = new MapSqlParameterSource();
        param.addValue("id", id);
        return jdbcTemplate.update("DELETE FROM terms WHERE id = :id", param);
    }
    @Override
    public List<Genres> findAll() {
        return jdbcTemplate.query("SELECT * FROM genres ORDER BY id;",
                new DataClassRowMapper<>(Genres.class));
    }
    @Override
    public Genres findByGenres(int id) {
        var param = new MapSqlParameterSource();
        param.addValue("id", id);
        var list = jdbcTemplate.query("SELECT * FROM genres WHERE id = :id " , param, new DataClassRowMapper<>(Genres.class));
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public int update(TermManagement change){
        var param = new MapSqlParameterSource();
        param.addValue("term_name", change.term_name());
        param.addValue("explain", change.explain());
        param.addValue("genre_id", change.genre_id());
        param.addValue("file", change.file());
        param.addValue("id", change.id());
        return jdbcTemplate.update("UPDATE terms SET term_name = :term_name, explain = :explain, genre_id = :genre_id, file = :file WHERE id = :id", param);
    }
    @Override
    public int updates(TermManagementNotFile changes){
        var param = new MapSqlParameterSource();
        param.addValue("term_name", changes.term_name());
        param.addValue("explain", changes.explain());
        param.addValue("genre_id", changes.genre_id());
        param.addValue("id", changes.id());
        return jdbcTemplate.update("UPDATE terms SET term_name = :term_name, explain = :explain, genre_id = :genre_id WHERE id = :id", param);
    }
    @Override
    public TermManagement findByTermId(String term_name) {
        var param = new MapSqlParameterSource();
        param.addValue("term_name",term_name);
        var list = jdbcTemplate.query("SELECT * FROM terms WHERE term_name = :term_name", param, new DataClassRowMapper<>(TermManagement.class));
        return list.isEmpty() ? null : list.get(0);
    }
    @Override
    public TermManagement findByFile(String file) {
        var param = new MapSqlParameterSource();
        param.addValue("file",file);
        var list = jdbcTemplate.query("SELECT * FROM terms WHERE file = :file", param, new DataClassRowMapper<>(TermManagement.class));
        return list.isEmpty() ? null : list.get(0);
    }
}
