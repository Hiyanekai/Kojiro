package com.example.Kojiro.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class TermForm {
    //用語集
    @NotBlank(message = "名前は必須です")
    @Length(min = 2,max = 20)
    public String term_name;
    @NotBlank(message = "説明文がありません")
    @Length(min = 2,max = 500)
    public String explain;

    public String file;
}
