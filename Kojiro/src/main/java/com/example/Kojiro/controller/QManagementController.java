package com.example.Kojiro.controller;

//QManagementController = QuestionManagementController(問題管理) 長くてすみません

import com.example.Kojiro.entity.TestQuestion;
import com.example.Kojiro.entity.question;
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

    private String[] successMesList={"","追加に成功","更新に成功","削除に成功"};

    private int successIndex=0;
    @Autowired
    QManagementService qManagementService;
    @Autowired
    private HttpSession session;
    @GetMapping("/quiz-management")
    public String menu(@RequestParam(name="keyword" ,defaultValue = "") String keyword, Model model) {
//        if (session.getAttribute("user")==null){
//            return "redirect:/login-test";
//        }
        if (keyword.isEmpty()){
            model.addAttribute("management", qManagementService.findAll());
            model.addAttribute("success",successMesList[successIndex]);
        }else {
            System.out.println(qManagementService.findBySentence(keyword));
            model.addAttribute("management", qManagementService.findBySentence(keyword));
        }
        return "quiz-management";
    }

    @GetMapping("quiz-detail/{id}")
    public String detail(@PathVariable("id") int id, Model model) {
        System.out.println(id);
        System.out.println(qManagementService.findById(id));
        // ロジックをServiceに任せる
        model.addAttribute("q_management", qManagementService.findById(id));
        return "quiz-detail";
    }

    @GetMapping("quiz-update/{id}")
    public String update1(@PathVariable("id") int id, Model model) {
        System.out.println(id);
        System.out.println(qManagementService.findById(id));
        // ロジックをServiceに任せる
        model.addAttribute("q_management", qManagementService.findById(id));
        return "quiz-update";
    }

    @PostMapping("quiz-update/{id}")
    public String update2(@PathVariable("id") int id, @Validated @ModelAttribute("q_management") QuizManagement update, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()) {
            return "quiz-update";
        }else {
            System.out.println(update);
            var conProduct1 = new TestQuestion(id,update.getGenre(),update.getSentence(),update.getAnswer(),update.getExplain(),update.getFile(),update.getScore());
            try{
                var result2 = qManagementService.update(conProduct1);
                System.out.println(conProduct1);
                successIndex=2;
                return "redirect:/quiz-management";
            }catch (RuntimeException e){
                model.addAttribute("q_management", qManagementService.findById(id));
                System.out.println("重複しましたよ");
                return "quiz-update";
            }
        }
    }

    @GetMapping("/quiz-delete/{id}")
    public String delete1(@PathVariable("id") int id){
        var result3 = qManagementService.delete(id);
        successIndex=3;
        return "redirect:/quiz-management";
    }

    @GetMapping("/quiz-add")
    public String index(@ModelAttribute("add") QuizManagement add) {
        return "quiz-add";
    }
    @PostMapping("/quiz-add")
    public String product1(@Validated @ModelAttribute("add") QuizManagement add, BindingResult bindingResult) {
        System.out.println(add);
        if(bindingResult.hasErrors()) {
            System.out.println("バリデーション");
            return "quiz-add";
        }else {
            System.out.println("バリデーションなし");
            var conProduct = new TestQuestion(0,add.getGenre(),add.getSentence(),add.getAnswer(),add.getExplain(),add.getFile(),add.getScore());
            try{
                var result1 = qManagementService.insert(conProduct);
                System.out.println(conProduct);
                successIndex=1;
                return "redirect:/quiz-management";
            }catch (RuntimeException e){
                System.out.println("重複しましたよ");
                return "quiz-add";
            }
        }
    }
}
