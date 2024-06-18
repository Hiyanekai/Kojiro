package com.example.Kojiro.dao;

import com.example.Kojiro.entity.Genres;
import com.example.Kojiro.entity.TermManagementDetail;
import com.example.Kojiro.entity.TermManagementNotFile;
import com.example.Kojiro.entity.TermAddition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PgTermManagementDetailDao implements TermManagementDetailDao {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Override
    public TermManagementDetail findById(int id) {
        var param = new MapSqlParameterSource();
        param.addValue("id", id);
        var list = jdbcTemplate.query("SELECT * FROM terms WHERE id = :id ORDER BY id" , param, new DataClassRowMapper<>(TermManagementDetail.class));
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
    public int update(TermManagementDetail change){
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
    public TermManagementDetail findByTermId(String term_name) {
        var param = new MapSqlParameterSource();
        param.addValue("term_name",term_name);
        var list = jdbcTemplate.query("SELECT * FROM terms WHERE term_name = :term_name", param, new DataClassRowMapper<>(TermManagementDetail.class));
        return list.isEmpty() ? null : list.get(0);
    }
    @Override
    public TermManagementDetail findByFile(String file) {
        var param = new MapSqlParameterSource();
        param.addValue("file", file);
        var list = jdbcTemplate.query("SELECT * FROM terms WHERE file = :file", param, new DataClassRowMapper<>(TermManagementDetail.class));
        return list.isEmpty() ? null : list.get(0);
    }
//
//    @Autowired
//    public NamedParameterJdbcTemplate jdbcTemplate;
//
//    @Override
//    public List<TermManagementDetail> findAll() {
//        return jdbcTemplate.query("SELECT * FROM terms ORDER BY id",
//                new DataClassRowMapper<>(TermManagementDetail.class));
//    }
//
//    @Override
//    public List<TermManagementDetail> findByTerm(String key) {
//        var param = new MapSqlParameterSource();
//        param.addValue("key", "%" + key + "%");
//        return jdbcTemplate.query("SELECT terms.id ,terms.term_name ,terms.explain,terms.file FROM terms WHERE terms.term_name like :key", param,
//                new DataClassRowMapper<>(TermManagementDetail.class));
//    }
//
//    @Override
//    public int termAddition(TermAddition user){
//        var param = new MapSqlParameterSource();
//        param.addValue("term_name", user.term_name());
//        param.addValue("explain", user.explain());
//        param.addValue("file", user.file());
//        return jdbcTemplate.update("INSERT INTO terms(term_name,explain,file) VALUES(:term_name,:explain,:file)", param);
//    }
//
//    @Override
//    public TermAddition findtermAddition(String term_name) {
//        var param = new MapSqlParameterSource();
//        param.addValue("term_name", term_name);
//        var list = jdbcTemplate.query("SELECT * FROM terms WHERE term_name=:term_name", param, new DataClassRowMapper<>(TermAddition.class));
//        return list.isEmpty() ? null : list.get(0);
//    }
}
