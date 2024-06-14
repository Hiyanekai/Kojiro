package com.example.Kojiro.controller;

import com.example.Kojiro.LoginForm;
import com.example.Kojiro.entity.SignUp;
import com.example.Kojiro.entity.users;
import com.example.Kojiro.service.LoginService;
import com.example.Kojiro.service.PgLoginService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Controller
public class LoginController {
    @Autowired
    private PgLoginService pgLoginService;

    @Autowired
    private HttpSession session;

    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    @GetMapping("/index")
    public String index1(@ModelAttribute("login") LoginForm loginForm) {
        return "index";
    }

    @PostMapping("index")
    public String index2(@Validated @ModelAttribute("login") LoginForm loginForm, BindingResult bindingResult, Model model) {
        var result = pgLoginService.findbylogin(loginForm.getUserId(), loginForm.getPassword());
        session.setAttribute("users", result);
        if (bindingResult.hasErrors()) {
            return "index";
        }else if (result != null) {
            return "redirect:/menu";
        } else {
            model.addAttribute("error", "IDまたはパスワードが不正です");
            return "index";
        }
    }

    @GetMapping("/sign-up")
    public String signUp(@ModelAttribute("signUp") LoginForm loginForm){
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String signUp2(@Validated @ModelAttribute("signUp")LoginForm loginForm, BindingResult bindingResult, Model model){
        var findByloginId = pgLoginService.findByloginId(loginForm.getUserId());
        if (bindingResult.hasErrors()){
            return "sign-up";
        }else if (findByloginId==null) {
            pgLoginService.signUp(new SignUp(loginForm.getUserId(), loginForm.getPassword(), 2,timestamp));
            return "sign-up-success";
        }else {
            model.addAttribute("error", "IDまたはパスワードが不正です");
            return "sign-up";
        }

    }

    @GetMapping("/sign-up-success")
    public String succese(@ModelAttribute("signupsuccess") LoginForm loginForm){
        return "sign-up-success";
    }
}
