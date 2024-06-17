package com.example.Kojiro.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record UserManagement(
        Integer id,
        @NotBlank(message = "ユーザーIDは必須")
        String user_id,
        @NotBlank(message = "パスワードは必須")
        String password,
        Integer role
) {
}
