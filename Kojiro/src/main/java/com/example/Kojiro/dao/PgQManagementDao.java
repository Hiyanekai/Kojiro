package com.example.Kojiro.dao;

import com.example.Kojiro.entity.Questions2points;
import com.example.Kojiro.entity.TestQuestion;
import com.example.Kojiro.entity.TestQuestionP2;
import com.example.Kojiro.entity.question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PgQManagementDao implements QManagementDao{

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<TestQuestion> findAll() {
//        return jdbcTemplate.query("SELECT questions.id, genres.genre_name AS genre, questions.sentence, questions.answer, questions.explain, questions.file, questions.score FROM questions INNER JOIN genres ON questions.genre_id = genres.id ORDER BY questions.id",
//                new DataClassRowMapper<>(TestQuestion.class));
        return jdbcTemplate.query("SELECT questions.id, genres.genre_name AS genre, questions.sentence, questions.answer, questions.explain, questions.file, questions.score FROM questions INNER JOIN genres ON questions.genre_id = genres.id ORDER BY questions.id",
                new DataClassRowMapper<>(TestQuestion.class));
    }

    @Override
    public List<TestQuestionP2> findAll2() {
        List<TestQuestionP2> questions = jdbcTemplate.query("SELECT questions.id, genres.genre_name AS genre, questions.sentence, questions.answer, questions.explain, questions.file, questions.score FROM questions INNER JOIN genres ON questions.genre_id = genres.id ORDER BY questions.id",
                new DataClassRowMapper<>(TestQuestionP2.class));
        List<TestQuestionP2> questionsP2 = jdbcTemplate.query("SELECT questions.id, genres.genre_name AS genre, questions.sentence, questions.answer, questions.explain, questions.file, questions.score FROM questions_2points questions INNER JOIN genres ON questions.genre_id = genres.id ORDER BY questions.id",
                new DataClassRowMapper<>(TestQuestionP2.class));
        questions.addAll(questionsP2);
        return questions;
    }

    @Override
    public List<TestQuestion> findBySentence(String sentence) {
        var param = new MapSqlParameterSource();
        param.addValue("keyword","%"+sentence+"%");
        return jdbcTemplate.query("SELECT questions.id, genres.genre_name AS genre, questions.sentence, questions.answer, questions.explain, questions.file, questions.score  FROM questions INNER JOIN genres ON questions.genre_id = genres.id WHERE questions.sentence like :keyword ORDER BY questions.id", param, new DataClassRowMapper<>(TestQuestion.class));
    }

    @Override
    public List<TestQuestionP2> findBySentence2(String sentence) {
        var param = new MapSqlParameterSource();
        param.addValue("keyword","%"+sentence+"%");
        var questions = jdbcTemplate.query("SELECT questions.id, genres.genre_name AS genre, questions.sentence, questions.answer, questions.explain, questions.file, questions.score  FROM questions INNER JOIN genres ON questions.genre_id = genres.id WHERE questions.sentence like :keyword ORDER BY questions.id", param, new DataClassRowMapper<>(TestQuestionP2.class));
        var questionsP2 = jdbcTemplate.query("SELECT questions.id, genres.genre_name AS genre, questions.sentence, questions.answer, questions.explain, questions.file, questions.score  FROM questions_2points questions INNER JOIN genres ON questions.genre_id = genres.id WHERE questions.sentence like :keyword ORDER BY questions.id",
                param, new DataClassRowMapper<>(TestQuestionP2.class));
        if(questions.isEmpty()){
            return questionsP2;
        } else if(!questions.isEmpty() && !questionsP2.isEmpty()){
            questions.addAll(questionsP2);
        }
        return questions;
    }

    @Override
    public TestQuestion findById(int id) {
        var param = new MapSqlParameterSource();
        param.addValue("id", id);
        var list = jdbcTemplate.query("SELECT questions.id, genres.genre_name AS genre, questions.sentence, questions.answer, questions.explain, questions.file, questions.score FROM questions INNER JOIN genres ON questions.genre_id = genres.id WHERE questions.id = :id ORDER BY questions.id", param, new DataClassRowMapper<>(TestQuestion.class));
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public TestQuestionP2 findById2(int id) {
        var param = new MapSqlParameterSource();
        param.addValue("id", id);
        var list = jdbcTemplate.query("SELECT questions.id, genres.genre_name AS genre, questions.sentence, questions.answer, questions.explain, questions.file, questions.score FROM questions_2points questions INNER JOIN genres ON questions.genre_id = genres.id WHERE questions.id = :id ORDER BY questions.id", param, new DataClassRowMapper<>(TestQuestionP2.class));
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public int update(TestQuestion question){
        var param = new MapSqlParameterSource();
        param.addValue("id",question.id());
        param.addValue("genre",Integer.parseInt(question.genre()));
        param.addValue("sentence", question.sentence());
        param.addValue("answer", question.answer());
        param.addValue("explain", question.explain());
        param.addValue("file", question.file());
        param.addValue("score", question.score());
        if(question.file().equals("")) {
            return jdbcTemplate.update("UPDATE questions SET genre_id = :genre, sentence = :sentence, answer = :answer, explain = :explain, score = :score WHERE id = :id", param);
        } else {
            param.addValue("file", question.file());
            return jdbcTemplate.update("UPDATE questions SET genre_id = :genre, sentence = :sentence, answer = :answer, explain = :explain, file = :file, score = :score WHERE id = :id", param);
        }
    }

    @Override
    public int update(TestQuestionP2 question){
        var param = new MapSqlParameterSource();
        param.addValue("id",question.id());
        param.addValue("genre",Integer.parseInt(question.genre()));
        param.addValue("sentence", question.sentence());
        param.addValue("answer", question.answer());
        param.addValue("explain", question.explain());
        param.addValue("score", question.score());
        if(question.file().equals("")) {
            return jdbcTemplate.update("UPDATE questions_2points SET genre_id = :genre, sentence = :sentence, answer = :answer, explain = :explain, score = :score WHERE id = :id", param);
        } else {
            param.addValue("file", question.file());
            return jdbcTemplate.update("UPDATE questions_2points SET genre_id = :genre, sentence = :sentence, answer = :answer, explain = :explain, file = :file, score = :score WHERE id = :id", param);
        }
    }

    @Override
    public int delete(int id) {
        var param = new MapSqlParameterSource();
        param.addValue("id", id);
        return jdbcTemplate.update("DELETE FROM questions WHERE id = :id", param);
    }

    @Override
    public int delete2(int id) {
        var param = new MapSqlParameterSource();
        param.addValue("id", id);
        return jdbcTemplate.update("DELETE FROM questions_2points WHERE id = :id", param);
    }

    @Override
    public int insert(TestQuestion question) {
        var param = new MapSqlParameterSource();
        param.addValue("genre",Integer.parseInt(question.genre()));
        param.addValue("sentence", question.sentence());
        param.addValue("answer", question.answer());
        param.addValue("explain", question.explain());
        param.addValue("file", question.file());
        param.addValue("score", question.score());
        if(question.file().equals("")) {
            param.addValue("file", null);
        }
        return jdbcTemplate.update("INSERT INTO questions(genre_id,sentence,answer,explain,file,score) VALUES(:genre, :sentence, :answer, :explain, :file, :score)", param);
    }

    @Override
    public int insert(Questions2points questions2points) {
        var param = new MapSqlParameterSource();
        param.addValue("genre",questions2points.genre_id());
        param.addValue("sentence", questions2points.sentence());
        param.addValue("answer", questions2points.answer());
        param.addValue("explain", questions2points.explain());
        param.addValue("file", questions2points.file());
        param.addValue("score", questions2points.score());
        if(questions2points.file().equals("")) {
            param.addValue("file", null);
        }
        return jdbcTemplate.update("INSERT INTO questions_2points(genre_id,sentence,answer,explain,file,score) VALUES(:genre, :sentence, :answer, :explain, :file, :score)", param);
    }
}
