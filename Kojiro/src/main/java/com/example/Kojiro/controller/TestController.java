package com.example.Kojiro.controller;

import com.example.Kojiro.entity.*;
import com.example.Kojiro.service.PgQuestionsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Controller
public class TestController {

    @Autowired
    private HttpSession session;

    @GetMapping("/test")
    public String test(@ModelAttribute("result") List<test_results> results,Model model){
        var service = new PgQuestionsService();//サービスクラスのオブジェクト作成
        List<testquestion> testData = service.findTest();//1点問題90問、2点問題5問の配列を取得するfindTestメソッドを呼び出す。
        model.addAttribute("testData",testData);
        //int times = 1;
        //var resultDB = service.findTestResult();
        //for(test_result test_results : resultDB) {//回答用ループ
        //  if (times != test_results.score_id()){//答えを比較して採点
        //      times += 1;
        //  }
        //}
        //model.addAttribute("times",times);
        return "index";
    }
    @PostMapping("/test")
    public String testResult(@ModelAttribute("result") List<test_results> results, Model model){
        var data = session.getAttribute("user");//セッションにあるuserタグのデータを取得
        users sessionUser = (users) session.getAttribute("user");//user型の変数にデータを格納

        var service = new PgQuestionsService();//サービスクラスのオブジェクト作成
        int score = 0;//合計得点計算用の変数
        List<questions> testData = new ArrayList<>();//回答表示用のリスト型配列を作成

        for(test_results test_results : results) {//回答用ループ
            questions question = service.findQuestion(test_results.q_id());//問題IDからテスト問題を取得
            if (question.answer() == test_results.user_select()){//答えを比較して採点
                score += question.score();//回答と同じの場合のみ点数を加算
            }
            else {//回答が間違えていた場合
                misses misses = new misses(0,test_results.q_id(),sessionUser.user_id());//ID(シリアルなので適当)、問題ID、ユーザー名を保持するmisses型の変数
                //service.insertMisses(misses);//missesクラスに問題を追加
            }
            if (test_results.flag() == 1){//問題にフラグが立てられていた場合
                flags flags = new flags(0,test_results.q_id(),sessionUser.user_id());//ID(シリアルなので適当)、問題ID、ユーザー名を保持するflags型の変数
                //service.insertFlags(flags);//flagクラスに問題を追加
            }
            testData.add(question);//回答表示用のリストに採点した問題を格納
        }


        Calendar cl = Calendar.getInstance();/////////////////////////////////////////////////
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");////今日の日付を取得
        String str1 = sdf1.format(cl.getTime());//////////////////////////////////////////////

        scores testScore = new scores(0, sessionUser.user_id(), score,str1);//受験日、ユーザーID、点数等の受験結果を保持する変数
        //service.insertScore(testScore);//受験結果をScoreテーブルに追加
        //service.insertTest_result(result);

        model.addAttribute("testData",testData);
        model.addAttribute("testScore",testScore);
        return "test";
    }
}
