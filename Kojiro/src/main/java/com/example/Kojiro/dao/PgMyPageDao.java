package com.example.Kojiro.dao;

import com.example.Kojiro.entity.*;
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
    public Scores findByIdForScores(int userId,int scoreId) {
        var param=new MapSqlParameterSource();
        param.addValue("user_id",userId);
        param.addValue("score_id",scoreId);
        var r =  jdbcTemplate.query("SELECT * FROM scores where user_id = :user_id AND score_id= :score_id",param,
                new DataClassRowMapper<>(Scores.class));
        return r.isEmpty()? null:r.get(0);
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

    @Override
    public List<Concern> ConcernFindMe(int userId){
        var param=new MapSqlParameterSource();
        param.addValue("user_id",userId);
        return jdbcTemplate.query("select flags.user_id,questions.sentence,cast(questions.answer as varchar(10)),questions.explain,questions.file\n" +
                "from flags\n" +
                "join questions\n" +
                "on flags.q_id = questions.id\n" +
                "where user_id = :user_id\n"+
                "union \n" +
                "select flags.user_id,questions_2points.sentence,questions_2points.answer,questions_2points.explain,questions_2points.file\n" +
                "from flags\n" +
                "join questions_2points\n" +
                "on flags.q_id_2points = questions_2points.id\n" +
                "where user_id = :user_id;",param,new DataClassRowMapper<>(Concern.class));
    }

    public List<ScoreDetail> ScoreDetailFindMe(int userId,int scoreId){
        var param=new MapSqlParameterSource();
        param.addValue("user_id",userId);
        param.addValue("score_id",scoreId);
        return jdbcTemplate.query("select scores.score_id \n" +
                ",questions.sentence,cast(questions.answer as varchar(10)),questions.explain,questions.file,questions.score as s\n" +
                ",test_results.user_select,test_results.score,test_results.flag,test_results.id as i,test_results.q_id\n" +
                "from test_results\n" +
                "join questions\n" +
                "on test_results.q_id=questions.id\n" +
                "join scores\n" +
                "on test_results.score_id=scores.score_id\n" +
                "where test_results.user_id = :user_id AND scores.score_id= :score_id \n" +
                "union\n" +
                "select scores.score_id\n" +
                ",questions_2points.sentence,questions_2points.answer,questions_2points.explain,questions_2points.file,questions_2points.score \n" +
                ",test_results_2points.user_select,test_results_2points.score,test_results_2points.flag,test_results_2points.id,test_results_2points.q_id \n" +
                "from test_results_2points\n" +
                "join questions_2points\n" +
                "on test_results_2points.q_id=questions_2points.id\n" +
                "join scores\n" +
                "on test_results_2points.score_id=scores.score_id\n" +
                "where test_results_2points.user_id= :user_id  AND scores.score_id= :score_id\n" +
                "order by s,i;\n",param,new DataClassRowMapper<>(ScoreDetail.class));
    }

}
