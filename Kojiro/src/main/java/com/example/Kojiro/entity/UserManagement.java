package com.example.Kojiro.entity;

import jakarta.validation.constraints.NotEmpty;

public record UserManagement(
        Integer id,
        String user_id,
        String password,
        Integer role
) {
}
