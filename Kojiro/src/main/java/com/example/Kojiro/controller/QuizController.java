package com.example.Kojiro.controller;

import com.example.Kojiro.entity.Questions;
import com.example.Kojiro.entity.Questions2points;
import com.example.Kojiro.entity.Users;
import com.example.Kojiro.service.GenresService;
import com.example.Kojiro.service.PgQuestionsForQuizService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Controller
public class QuizController {

    @Autowired
    private GenresService genresService;
    @Autowired
    private PgQuestionsForQuizService pgQuestionsForQuizService;

    @Autowired
    private HttpSession session;
    @Autowired
    private HttpServletRequest request;

    // 10問の問題を格納するリスト
    List<Questions2points> quizzes;
    // 問題ページの問題数標示と問題リストの要素数として使用。
    public static int index = 0;


    @GetMapping("/quiz-select")
    public String quizSelect(Model model){
        if(request.getSession(false)==null) return "redirect:/index";
        index = 0;
        model.addAttribute("genres", genresService.findByStep(1));
        model.addAttribute("genres2", genresService.findByStep(2));
        return "quiz-select";
    }

    @GetMapping("/quiz/{gId}")
    public String quiz(@PathVariable("gId") int gId, Model model){
        if(request.getSession(false)==null) return "redirect:/index";
//        model.addAttribute("gId", gId);
//        System.out.println(pgQuestionsForQuizService.findById(345).sentence().split("\r")[0]);
        if(index == 0) {
            if(gId == 30){
                quizzes = pgQuestionsForQuizService.quizGetBy2points(gId);
            } else if(gId == 99){
                quizzes = pgQuestionsForQuizService.findRandom();
            } else if(gId == 100) {
                var user = (Users)session.getAttribute("users");
                quizzes = pgQuestionsForQuizService.findAllForFlag(user.id());
            } else if(gId == 999){
                var user = (Users)session.getAttribute("users");
                quizzes = pgQuestionsForQuizService.findAllForMiss(user.id());
            } else if(gId > 30){
                return "redirect:../quiz-select";
            }
            else {
                quizzes = pgQuestionsForQuizService.findByGenre(gId);
                quizFlag = true;
//                quizzes.add(pgQuestionsForQuizService.findById(345));
            }
        }
        if(!quizzes.isEmpty()) {
//            if (quizzes.isEmpty()) return "redirect:../quiz-select";
            model.addAttribute("point2", quizzes.get(index).sentence().indexOf("\n"));
            model.addAttribute("genres", genresService.findAll());
            model.addAttribute("qNum", index + 1);
//            System.out.println(quizzes.size());
            model.addAttribute("questions", quizzes);
            // データベースにファイルパスがない場合、画像を表示しない
            if (quizzes.get(index).file() != null && !quizzes.get(index).file().equals("")) {
                File img = new File("./Kojiro/src/main/resources/static/images/" + quizzes.get(index).file());
                try {
                    byte[] byteImg = Files.readAllBytes(img.toPath());
                    String base64Data = Base64.getEncoder().encodeToString(byteImg);
                    model.addAttribute("base64Data", "data:img/png;base64," + base64Data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            return "/questions-not-found";
        }
        return quizzes.get(qNum).genre_id()==30? "quiz-2points" : "quiz";
    }

    @GetMapping("/quiz/{gId}/next")
    public String indexAdd(@PathVariable("gId") int gId, Model model){
        if(request.getSession(false)==null) return "redirect:/index";
        index++;
        if(index>quizzes.size()-1) {
            index=0;
            return "redirect:/menu";
        }
        return "redirect:/quiz/" + gId;
    }

    @GetMapping("/quiz-answer/{gId}/{ans}")
    public String quizAnswer(@PathVariable("gId") int gId,
                             @PathVariable("ans") String ans, Model model){
        if(request.getSession(false)==null) return "redirect:/index";
//        model.addAttribute("ans", ans);
        if(quizzes.isEmpty()) return "redirect:../../quiz-select";
        var correct = quizzes.get(index).answer().replaceAll("0", "〇").replaceAll("1", "×");
//        correct = quizzes.get(index).answer().replaceAll("1", "×");
        model.addAttribute("point2", quizzes.get(index).sentence().indexOf("\n"));
        model.addAttribute("genres", genresService.findAll());
        model.addAttribute("qNum", index+1);
        model.addAttribute("correct", correct);
        model.addAttribute("questions", quizzes);
        // データベースにファイルパスがない場合、画像を表示しない
        if(quizzes.get(qNum).file()!=null && !quizzes.get(qNum).file().equals("")) {
            File img = new File("./Kojiro/src/main/resources/static/images/" + quizzes.get(qNum).file());
            try {
                byte[] byteImg = Files.readAllBytes(img.toPath());
                String base64Data = Base64.getEncoder().encodeToString(byteImg);
                model.addAttribute("base64Data", "data:img/png;base64," + base64Data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        return "quiz-answer";
        return gId==100||gId==999? "quiz-flag" : "quiz-answer";
    }

//    @GetMapping("/quiz-flag/{gId}/{ans}")
//    public String quizFlag(@PathVariable("gId") int gId,
//                           @PathVariable("ans") String ans, Model model){
//        var correct = quizzes.get(index).answer().replaceAll("0", "〇").replaceAll("1", "×");
////        correct = quizzes.get(index).answer().replaceAll("1", "×");
//        model.addAttribute("point2", quizzes.get(index).sentence().indexOf("\n"));
//        model.addAttribute("genres", genresService.findAll());
//        model.addAttribute("qNum", index+1);
//        model.addAttribute("correct", correct);
//        model.addAttribute("questions", quizzes);
//        return "quiz-flag";
//    }

    @GetMapping("/delete-flag/{id}/{gId}")
    public String deleteFlag(@PathVariable("id") int id,
                             @PathVariable("gId") int gId,
                             @PathVariable("qNum") int qNum, Model model){
        if(request.getSession(false)==null) return "redirect:/index";
        var result = pgQuestionsForQuizService.delFlagQuestion(id, gId);
        int next = qNum + 1;
        return "redirect:/quiz/"+100+"/"+next;

    }

    @GetMapping("/delete-miss/{id}/{gId}/{qNum}")
    public String deleteMiss(@PathVariable("id") int id,
                             @PathVariable("gId") int gId,
                             @PathVariable("qNum") int qNum, Model model){
        if(request.getSession(false)==null) return "redirect:/index";
        var result = pgQuestionsForQuizService.delMissQuestion(id, gId);
        System.out.println(result);
        int next = qNum + 1;
        return "redirect:/quiz/"+999+"/"+next;
    }

    @GetMapping("/return-menu")
    public String returnMenu(){
        if(request.getSession(false)==null) return "redirect:/index";
        index = 0;
        quizFlag = false;
        return "redirect:/menu";
    }

    @GetMapping("/questions-not-found")
    public String notFound(){
        if(request.getSession(false)==null) return "redirect:/index";
        return "/questions-not-found";
    }
}
