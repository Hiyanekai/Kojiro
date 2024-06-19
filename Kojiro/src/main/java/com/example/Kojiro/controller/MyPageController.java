package com.example.Kojiro.controller;

import com.example.Kojiro.dao.MyPageDao;
import com.example.Kojiro.entity.SignUp;
import com.example.Kojiro.entity.Users;
import com.example.Kojiro.entity.Weakness;
import com.example.Kojiro.form.MypageForm;
import com.example.Kojiro.service.MyPageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MyPageController {
    @Autowired
    private HttpSession session;
    @Autowired
    MyPageService myPageService;

    @GetMapping("/mypage")
    public String mypage(Model model){
        var user = (Users)session.getAttribute("users");
        model.addAttribute("user",myPageService.findById(user.id()));
        model.addAttribute("userDate", user.created_at().split(" ")[0]);
        return "mypage";
    }

    @GetMapping("/past-data")
    public String result(Model model){
        var user = (Users)session.getAttribute("users");
        model.addAttribute("dataList",myPageService.findMe(user.id()));

        return "past-data";
    }


    @GetMapping("/weakness")
    public String weakness(Model model){
        var user = (Users)session.getAttribute("users");
        model.addAttribute("missList",myPageService.WeaknessFindMe(user.id()));

        return "weakness";
    }





}
