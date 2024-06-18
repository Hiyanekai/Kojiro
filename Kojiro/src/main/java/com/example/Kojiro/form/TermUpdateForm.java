package com.example.Kojiro.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


@Data
public class TermUpdateForm {
    Integer id;
    Integer genre_id;
    @NotBlank(message = "用語名は必須")
    String term_name;
    @NotBlank(message = "解説は必須")
    String explain;
    MultipartFile file;
}
