package com.example.Kojiro.controller;

import com.example.Kojiro.entity.Users;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpSession session;

    @GetMapping("/menu")
    public String menu(Model model){
        if(request.getSession(false)==null) return "redirect:/index";
        QuizController.index = 0;
        var user = (Users)session.getAttribute("users");
        System.out.println(user.role());
        model.addAttribute("user", user);
        return "menu";
    }
}
