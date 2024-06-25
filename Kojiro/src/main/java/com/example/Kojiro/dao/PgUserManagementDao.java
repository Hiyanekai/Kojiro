package com.example.Kojiro.dao;

import com.example.Kojiro.entity.UserManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PgUserManagementDao implements UserManagementDao {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<UserManagement> findAll() {
        return jdbcTemplate.query("SELECT * FROM users ORDER BY id",
                new DataClassRowMapper<>(UserManagement.class));
    }

    @Override
    public List<UserManagement> search(String user_id) {
        var param = new MapSqlParameterSource();
        param.addValue("user_id", user_id);
        return jdbcTemplate.query("SELECT * FROM users WHERE  user_id LIKE '%' || :user_id || '%' ORDER BY id",
                param, new DataClassRowMapper<>(UserManagement.class));
    }

    @Override
    public UserManagement findById(int id) {
        var param = new MapSqlParameterSource();
        param.addValue("id", id);
        var list = jdbcTemplate.query("SELECT * FROM users WHERE id = :id ORDER BY id" , param, new DataClassRowMapper<>(UserManagement.class));
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public int delete(int id){
        var param = new MapSqlParameterSource();
        param.addValue("id", id);
        return jdbcTemplate.update("DELETE FROM users WHERE id = :id", param);
    }
    @Override
    public int update(UserManagement change){
        var param = new MapSqlParameterSource();
        param.addValue("user_id", change.user_id());
        param.addValue("password", change.password());
        param.addValue("id", change.id());
        return jdbcTemplate.update("UPDATE users SET user_id = :user_id, password = :password WHERE id = :id", param);
    }

    @Override
    public UserManagement findByUserId(String user_id) {
        var param = new MapSqlParameterSource();
        param.addValue("user_id",user_id);
        var list = jdbcTemplate.query("SELECT * FROM users WHERE user_id = :user_id", param, new DataClassRowMapper<>(UserManagement.class));
        return list.isEmpty() ? null : list.get(0);
    }

}
