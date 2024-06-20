package com.example.Kojiro.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpSession session;

    @GetMapping("/menu")
    public String menu(){
        if(request.getSession(false)==null) return "redirect:/index";
        return "menu";
    }
}
