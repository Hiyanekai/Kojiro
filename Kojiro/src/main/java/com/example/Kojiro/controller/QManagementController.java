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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

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
//        if (session.getAttribute("user")==null){//ユーザーのセッション判定
//            return "redirect:/login-test";
//        }
        if (keyword.isEmpty()){//検索欄にキーワードなしは全部出す
            model.addAttribute("management", qManagementService.findAll());
            model.addAttribute("success",successMesList[successIndex]);
        }else {//検索欄にキーワード入れると問題文から抽出
            System.out.println(qManagementService.findBySentence(keyword));
            model.addAttribute("management", qManagementService.findBySentence(keyword));
        }
        return "quiz-management";
    }

    @GetMapping("quiz-detail/{id}")//id指定で詳細のページを出す
    public String detail(@PathVariable("id") int id, Model model) {
        System.out.println(id);
        System.out.println(qManagementService.findById(id));
        var q = qManagementService.findById(id);
        model.addAttribute("q_management",q);
        if(q.file()!=null && !q.file().equals("")) {
            File img = new File("./Kojiro/src/main/resources/static/images/" + q.file());
            try {
                byte[] byteImg = Files.readAllBytes(img.toPath());
                String base64Data = Base64.getEncoder().encodeToString(byteImg);
                model.addAttribute("base64Data", "data:img/png;base64," + base64Data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "quiz-detail";
    }

    @GetMapping("quiz-update/{id}")//id指定で更新のページを出す
    public String update1(@PathVariable("id") int id, Model model) {
        System.out.println(id);
        System.out.println(qManagementService.findById(id));
        model.addAttribute("q_management", qManagementService.findById(id));
        return "quiz-update";
    }

    @PostMapping("quiz-update/{id}")
    public String update2(@PathVariable("id") int id, @Validated @ModelAttribute("q_management") QuizManagement update, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()) {//バリデーションチェック
            return "quiz-update";
        }else {
            System.out.println(update);
            var conProduct1 = new TestQuestion(id,update.getGenre(),update.getSentence(),update.getAnswer(),update.getExplain(),update.getFile(),update.getScore());
            try{//正常に更新したとき
                var result2 = qManagementService.update(conProduct1);
                System.out.println(conProduct1);
                successIndex=2;
                return "redirect:/quiz-management";
            }catch (RuntimeException e){//id(主キー)が重複したときエラーコードを出して、更新のページに戻す
                model.addAttribute("q_management", qManagementService.findById(id));
                System.out.println("重複しましたよ");
                return "quiz-update";
            }
        }
    }

    @GetMapping("/quiz-delete/{id}")//問題の削除(idを指定)
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
        if(bindingResult.hasErrors()) {//バリデーションチェック
            System.out.println("バリデーション");
            return "quiz-add";
        }else {
            System.out.println("バリデーションなし");
            var conProduct = new TestQuestion(0,add.getGenre(),add.getSentence(),add.getAnswer(),add.getExplain(),add.getFile(),add.getScore());
            try{//正常に問題追加したとき
                var result1 = qManagementService.insert(conProduct);
                System.out.println(conProduct);
                successIndex=1;
                return "redirect:/quiz-management";
            }catch (RuntimeException e){//id(主キー)が重複したときエラーコードを出して、追加のページに戻す
                System.out.println("重複しましたよ");
                return "quiz-add";
            }
        }
    }
}
