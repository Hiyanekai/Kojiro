package com.example.Kojiro.dao;

import com.example.Kojiro.LoginForm;
import com.example.Kojiro.entity.SignUp;
import com.example.Kojiro.entity.users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public  class PgLoginDao implements LoginDao{
    @Autowired
    public NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public users findbylogin(String userId, String password) {
        var param = new MapSqlParameterSource();
        param.addValue("user_id", userId);
        param.addValue("password", password);
        var list = jdbcTemplate.query("SELECT * FROM users WHERE user_id = :user_id AND password =:password", param, new DataClassRowMapper<>(users.class));
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public int signUp(SignUp signup) {
        var param = new MapSqlParameterSource();
        param.addValue("user_id",signup.user_id());
        param.addValue("password", signup.password());
        param.addValue("created_at", signup.created_at());
        return jdbcTemplate.update("INSERT INTO users(user_id,password,role,created_at) VALUES(:user_id,:password,2,:created_at)", param);
    }

    @Override
    public SignUp findByloginId(String user_id){
        var param = new MapSqlParameterSource();
        param.addValue("user_id", user_id);
        var list = jdbcTemplate.query("SELECT * FROM users WHERE user_id=:user_id", param,new DataClassRowMapper<>(SignUp.class));
        return list.isEmpty() ? null : list.get(0);
    }
}
