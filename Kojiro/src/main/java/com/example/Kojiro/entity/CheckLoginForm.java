<<<<<<<< HEAD:Kojiro/src/main/java/com/example/Kojiro/form/CheckLoginForm.java
package com.example.Kojiro.form;
========
package com.example.Kojiro.entity;
>>>>>>>> eb7a8e6ae8c8b3861aee91aedc7abdbb7cb698fd:Kojiro/src/main/java/com/example/Kojiro/entity/CheckLoginForm.java

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
@Data
public class CheckLoginForm {
        //パスワード再確認用
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
