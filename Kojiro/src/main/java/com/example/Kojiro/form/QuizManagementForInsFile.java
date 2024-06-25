package com.example.Kojiro.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public class QuizManagementForInsFile {

    @NotEmpty(message = "問題は必須入力です")
    private String sentence;

    @NotNull(message = "解答は必須入力です")
    private int answer;

    @NotEmpty(message = "解説は必須入力です")
    private String explain;

    @NotEmpty(message = "ジャンルは必須入力です")
    private String genre;

    private MultipartFile file;

    @NotNull(message = "スコアは必須入力です")
    private int score;
}