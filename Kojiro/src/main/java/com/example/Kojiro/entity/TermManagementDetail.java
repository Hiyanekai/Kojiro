package com.example.Kojiro.entity;

import jakarta.validation.constraints.NotBlank;

public record TermManagementDetail(
        Integer id,
        Integer genre_id,
        @NotBlank(message = "用語名は必須")
        String term_name,
        @NotBlank(message = "解説は必須")
        String explain,
        String file
) {
//public record TermManagement (int id,String term_name,String explain,String file){
}
