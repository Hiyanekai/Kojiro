package com.example.Kojiro.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QuizManagementStrAns {
    @NotEmpty(message = "問題は必須入力です")
    private String sentence;

    @NotNull(message = "解答は必須入力です")
    private String answer;

    @NotEmpty(message = "解説は必須入力です")
    private String explain;

    @NotEmpty(message = "ジャンルは必須入力です")
    private String genre;

    private String file;

    @NotNull(message = "スコアは必須入力です")
    private int score;
}
