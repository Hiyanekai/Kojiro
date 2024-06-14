package com.example.Kojiro.controller;

//QManagementController = QuestionManagementController(問題管理) 長くてすみません

import com.example.Kojiro.entity.TestQuestion;
import com.example.Kojiro.form.QuizManagement;
import com.example.Kojiro.service.QManagementService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("quiz-detail/{id}")
    public String userList(@PathVariable("id") int id, Model model) {
        System.out.println(id);
        System.out.println(qManagementService.findById(id));
        // ロジックをServiceに任せる
        model.addAttribute("q_management", qManagementService.findById(id));
        return "quiz-detail";
    }

    @GetMapping("update/{id}")
    public String update1(@PathVariable("id") int id, Model model) {
        System.out.println(id);
        System.out.println(qManagementService.findById(id));
        // ロジックをServiceに任せる
        model.addAttribute("q_management", qManagementService.findById(id));
        return "update";
    }

    @PostMapping("update/{id}")
    public String update2(@PathVariable("id") int id, @Validated @ModelAttribute("q_management") QuizManagement update, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()) {
            return "update";
        }else {
            System.out.println(update);
            var conProduct1 = new TestQuestion(id,update.getGenre(),update.getSentence(),update.getAnswer(),update.getExplain(),update.getFile(),update.getScore());
            try{
                var result2 = TestQuestion.update(conProduct1);
                System.out.println(conProduct1);
                return "redirect:/success";
            }catch (RuntimeException e){
                model.addAttribute("q_management", qManagementService.findById(id));
                System.out.println("重複しましたよ");
                return "update";
            }
        }
    }
}
