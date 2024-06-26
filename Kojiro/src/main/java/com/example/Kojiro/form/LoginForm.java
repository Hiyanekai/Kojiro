package com.example.Kojiro.form;


import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class LoginForm {
    //ログイン用
    @NotEmpty(message = "ユーザー名は必須です")
    @Length(max = 20)
    public String userId;
    @NotEmpty(message = "パスワードは必須です")
    @Length(max = 50)
    public String password;

}
