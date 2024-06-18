package com.example.Kojiro.entity;

import jakarta.validation.constraints.NotBlank;

public record TermManagementNotFile(
        Integer id,
        @NotBlank(message = "用語名は必須")
        String term_name,
        @NotBlank(message = "解説は必須")
        String explain
) {
}
