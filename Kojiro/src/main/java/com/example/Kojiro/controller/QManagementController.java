package com.example.Kojiro.controller;

//QManagementController = QuestionManagementController(問題管理) 長くてすみません

import com.example.Kojiro.service.QManagementService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class QManagementController {

    @Autowired
    QManagementService qManagementService;
    @Autowired
    private HttpSession session;
    @GetMapping("/quiz-management")
    public String userList(@RequestParam(name="keyword" ,defaultValue = "") String keyword, Model model) {
//        if (session.getAttribute("user")==null){
//            return "redirect:/login-test";
//        }
        if (keyword.isEmpty()){
            model.addAttribute("management", qManagementService.findAll());
        }else {
            System.out.println(qManagementService.findBySentence(keyword));
            model.addAttribute("management", qManagementService.findBySentence(keyword));
        }
        return "quiz-management";
    }
}
