package com.example.Kojiro.dao;

import com.example.Kojiro.entity.Scores;
import com.example.Kojiro.entity.SignUp;
import com.example.Kojiro.entity.Users;
import com.example.Kojiro.entity.Weakness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PgMyPageDao implements MyPageDao {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Override
    public Users findById(int id){
        var param = new MapSqlParameterSource();
        param.addValue("id", id);
        var list = jdbcTemplate.query("SELECT * FROM users WHERE id = :id", param, new DataClassRowMapper<>(Users.class));
        return list.isEmpty() ? null : list.get(0);
    }
    @Override
    public List<Scores> findAll() {
        return jdbcTemplate.query("SELECT score,test_date  FROM scores",
                new DataClassRowMapper<>(Scores.class));
    }
    @Override
    public List<Scores> findMe(int userId) {
        var param=new MapSqlParameterSource();
        param.addValue("user_id",userId);
        return jdbcTemplate.query("SELECT * FROM scores where user_id = :user_id",param,
                new DataClassRowMapper<>(Scores.class));
    }

    @Override
    public List<Weakness> WeaknessFindMe(int userId){
        var param=new MapSqlParameterSource();
        param.addValue("user_id",userId);
        return jdbcTemplate.query("select *, round(count / (sum(count) over()) * 100, 2) mistake_rate\n" +
                        "from \n" +
                        "(\n" +
                        "    select miss.user_id,questions.genre_id, max(genre_name) genre_name ,count(questions.genre_id) count\n" +
                        "    from miss\n" +
                        "    join questions  \n" +
                        "    on miss.q_id = questions.id \n" +
                        "        join genres\n" +
                        "    on questions.genre_id = genres.id\n" +
                        "    where miss.user_id = :user_id \n" +
                        "    group by miss.user_id,genre_id\n" +
                        "    union\n" +
                        "    select miss.user_id,questions_2points.genre_id,max(genre_name)genre_name,count(questions_2points.genre_id)count\n" +
                        "    from miss\n" +
                        "    join questions_2points\n" +
                        "    on miss.q_id_2points = questions_2points.id\n" +
                        "        join genres\n" +
                        "    on questions_2points.genre_id = genres.id    \n" +
                        "    where miss.user_id = :user_id \n" +
                        "    group by miss.user_id,genre_id \n" +
                        ") tmp\n" +
                        "    order by count DESC"
                ,param,
                new DataClassRowMapper<>(Weakness.class));
    }

}
