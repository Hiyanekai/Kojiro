package com.example.Kojiro.controller;

import com.example.Kojiro.entity.Questions;
import com.example.Kojiro.service.GenresService;
import com.example.Kojiro.service.PgQuestionsForQuizService;
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
import java.util.Base64;
import java.util.List;

@Controller
public class QuizController {

    @Autowired
    private GenresService genresService;
    @Autowired
    private PgQuestionsForQuizService pgQuestionsForQuizService;

    // 10問の問題を格納するリスト
    List<Questions> quizzes;
    // 問題ページの問題数標示と問題リストの要素数として使用。
    int index = 0;


    @GetMapping("/quiz-select")
    public String quizSelect(Model model){
        index = 0;
        model.addAttribute("genres", genresService.findByStep(1));
        model.addAttribute("genres2", genresService.findByStep(2));
        return "quiz-select";
    }

    @GetMapping("/quiz/{gId}")
    public String quiz(@PathVariable("gId") int gId, Model model){
//        model.addAttribute("gId", gId);
        if(index == 0) {
            if(gId == 99){
                quizzes = pgQuestionsForQuizService.findRandom();
            } else if(gId == 100) {
                quizzes = pgQuestionsForQuizService.findByGenre(1);
            } else if(gId > 29){
                return "redirect:../quiz-select";
            }
            else {
                quizzes = pgQuestionsForQuizService.findByGenre(gId);
            }
        }
        if(quizzes == null) return "redirect:../quiz-select";
        model.addAttribute("genres", genresService.findAll());
        model.addAttribute("qNum", index+1);
        model.addAttribute("questions", quizzes);
        // データベースにファイルパスがない場合、画像を表示しない
        if(quizzes.get(index).file()!=null && !quizzes.get(index).file().equals("")) {
            File img = new File("./Kojiro/src/main/resources/static/images/" + quizzes.get(index).file());
            try {
                byte[] byteImg = Files.readAllBytes(img.toPath());
                String base64Data = Base64.getEncoder().encodeToString(byteImg);
                model.addAttribute("base64Data", "data:img/png;base64," + base64Data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "quiz";
    }

    @GetMapping("/quiz/{gId}/next")
    public String indexAdd(@PathVariable("gId") int gId, Model model){
        index++;
        if(index>9) index=0;
        return "redirect:/quiz/" + gId;
    }

    @GetMapping("/quiz-answer/{gId}/{ans}")
    public String quizAnswer(@PathVariable("gId") int gId,
                             @PathVariable("ans") String ans, Model model){
//        model.addAttribute("ans", ans);
        if(quizzes == null) return "redirect:../../quiz-select";
        var correct = quizzes.get(index).answer()==0? "〇" : "×";
        model.addAttribute("genres", genresService.findAll());
        model.addAttribute("qNum", index+1);
        model.addAttribute("correct", correct);
        model.addAttribute("questions", quizzes);
        // データベースにファイルパスがない場合、画像を表示しない
        if(quizzes.get(index).file()!=null && !quizzes.get(index).file().equals("")) {
            File img = new File("./Kojiro/src/main/resources/static/images/" + quizzes.get(index).file());
            try {
                byte[] byteImg = Files.readAllBytes(img.toPath());
                String base64Data = Base64.getEncoder().encodeToString(byteImg);
                model.addAttribute("base64Data", "data:img/png;base64," + base64Data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "quiz-answer";
    }

    @GetMapping("quiz-flag")
    public String quizFlag(){
        return "quiz-flag";
    }
}
