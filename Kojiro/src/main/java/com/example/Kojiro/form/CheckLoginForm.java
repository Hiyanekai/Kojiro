package com.example.Kojiro.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
@Data
public class CheckLoginForm {

        @NotEmpty(message = "IDは必須です")
        @Length(max = 20)
        public String userId;
        @NotEmpty(message = "PASSは必須です")
        @Length(max = 50)
        public String password;
        @NotEmpty(message = "PASSは必須です")
        @Length(max = 50)
        public String repassword;
}
