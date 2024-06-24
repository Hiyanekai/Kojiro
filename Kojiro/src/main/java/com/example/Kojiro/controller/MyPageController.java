package com.example.Kojiro.controller;

import com.example.Kojiro.dao.MyPageDao;
import com.example.Kojiro.entity.ScoreDetail;
import com.example.Kojiro.entity.SignUp;
import com.example.Kojiro.entity.Users;
import com.example.Kojiro.entity.Weakness;
import com.example.Kojiro.form.MypageForm;
import com.example.Kojiro.form.ScoresForm;
import com.example.Kojiro.service.MyPageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MyPageController {
    @Autowired
    private HttpSession session;
    @Autowired
    MyPageService myPageService;

    @Autowired
    private HttpServletRequest request;

    @GetMapping("/mypage")
    public String mypage(Model model){
        if(request.getSession(false)==null) return "redirect:/index";
        var user = (Users)session.getAttribute("users");
        model.addAttribute("user",myPageService.findById(user.id()));
        model.addAttribute("userDate", user.created_at().split(" ")[0]);
        return "mypage";
    }

    @GetMapping("/past-data")
    public String result(Model model){
        if(request.getSession(false)==null) return "redirect:/index";
        var user = (Users)session.getAttribute("users");
        model.addAttribute("dataList",myPageService.findMe(user.id()));

        return "past-data";
    }


    @GetMapping("/weakness")
    public String weakness(Model model){
        if(request.getSession(false)==null) return "redirect:/index";
        var user = (Users)session.getAttribute("users");
        var missList = myPageService.WeaknessFindMe(user.id());
        System.out.println(missList==null);

        double othersRate = 0.0;
        List<String> genreNames = new ArrayList<>();
        List<Double> genreRates = new ArrayList<>();
        for(var i = 0; i < missList.size(); i++){
            if(i < 10) {
                genreNames.add(missList.get(i).genre_name());
                genreRates.add(missList.get(i).mistake_rate());
            } else {
                othersRate += missList.get(i).mistake_rate();
            }
        }
        genreNames.add("その他");
        genreRates.add(othersRate);
        model.addAttribute("missList",myPageService.WeaknessFindMe(user.id()));
        model.addAttribute("genreNames", genreNames);
        model.addAttribute("genreRates", genreRates);

        return "weakness";
    }

    @GetMapping("/concern")
    public String concern(Model model){
        if(request.getSession(false)==null) return "redirect:/index";
        var user = (Users)session.getAttribute("users");
        model.addAttribute("concernList",myPageService.ConcernFindMe(user.id()));
        return "concern";
    }


    @GetMapping("/scoredetail/{id}")
    public String scoredetail(@PathVariable("id") int scoreId, Model model){
        if(request.getSession(false)==null) return "redirect:/index";
        var user=(Users)session.getAttribute("users");
        var testResult = myPageService.ScoreDetailFindMe(user.id(),scoreId);
//        var times =myPageService.findMe(user.id());
//        for(int i = 0;i < times.size();i++){
//            if(scoreId==times.get(i).id()){
//                model.addAttribute("times",i+1)
//            }
//        }
        model.addAttribute("users", user);
        model.addAttribute("scoredetailList",testResult);
        model.addAttribute("scores",myPageService.findByIdForScores(user.id(),scoreId));




        return "scoredetail";

    }




}
