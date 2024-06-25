package com.example.Kojiro.form;

import lombok.Data;

@Data
public class ScoresForm {
    int scores_id;
    String sentence;
    String answer;
    String explain;
    String file;
    int score;
    int user_select;
    int judge;
    int flag;
    int test_results_id;
}
