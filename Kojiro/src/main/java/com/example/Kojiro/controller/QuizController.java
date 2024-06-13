package com.example.Kojiro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuizController {

    // 10問の問題を格納するリスト
    List<Integer> quizzes;
    // 問題ページの問題数標示と問題リストの要素数として使用。
    int index = 0;


    @GetMapping("/quiz-select")
    public String quizSelect(Model mode){
        index = 0;
        return "quiz-select";
    }

    @GetMapping("/quiz/{gId}")
    public String quiz(@PathVariable("gId") int gId, Model model){
//        model.addAttribute("gId", gId);
        model.addAttribute("qNum", index+1);
        return "quiz";
    }

    @GetMapping("/quiz/{gId}/{ans}")
    public String quizAnswer(@PathVariable("gId") int gId,
                             @PathVariable("ans") String ans, Model model){
//        model.addAttribute("ans", ans);
        model.addAttribute("qNum", index+1);
        index++;
        if(index>11) index=0;
        return "quiz-answer";
    }

    @GetMapping("quiz-flag")
    public String quizFlag(){
        return "quiz-flag";
    }
}
