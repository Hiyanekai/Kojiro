package com.example.Kojiro.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Data
public class TermForm {
    //用語集
    @NotBlank(message = "名前は必須です")
    public String term_name;
    @NotBlank(message = "説明文がありません")
    public String explain;

    public MultipartFile file;
}
