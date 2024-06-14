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
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Controller
public class TestController {

    @Autowired
    private HttpSession session;

    @GetMapping("test")
    public String test(@ModelAttribute("result") TestInput results, Model model){
        var service = new PgQuestionsService();//サービスクラスのオブジェクト作成
        List<TestQuestion> testData = service.findTest();//1点問題90問、2点問題5問の配列を取得するfindTestメソッドを呼び出す。
        model.addAttribute("testData",testData);
        testData.forEach(System.out::println);

        return "test";
    }
    @PostMapping("/result")
    public String testResult(@ModelAttribute("result") TestInput results,Model model){
//        var data = session.getAttribute("user");//セッションにあるuserタグのデータを取得
//        Users sessionUser = (Users) session.getAttribute("user");//user型の変数にデータを格納

        //テスト回数を判定
        //int times = 1;
        //var resultDB = service.findTestResult();
        //for(test_result test_results : resultDB) {//回答用ループ
        //  if (times != test_results.score_id()){//答えを比較して採点
        //      times += 1;
        //  }
        //}

        var service = new PgQuestionsService();//サービスクラスのオブジェクト作成
        int score = 0;//合計得点計算用の変数
        List<Questions> testData = new ArrayList<>();//回答表示用のリスト型配列を作成
        List<Integer> ans = new ArrayList<>();
        List<TestInput> list = results.getInputList();

        for(TestInput test_input : list) {//回答用ループ
            Questions question = service.findQuestion(test_input.getQ_id());//問題IDからテスト問題を取得
            if (question.answer() == test_input.getUser_select()){//答えを比較して採点
                score += question.score();//回答と同じの場合のみ点数を加算
                ans.add(0);//正解は0
            }
            else {//回答が間違えていた場合
//                Misses misses = new Misses(0,test_input.q_id(),sessionUser.user_id());//ID(シリアルなので適当)、問題ID、ユーザー名を保持するmisses型の変数
                //service.insertMisses(misses);//missesクラスに問題を追加
                ans.add(1);//正解は1
            }
            if (test_input.getFlag() == 1){//問題にフラグが立てられていた場合
//                Flags flags = new Flags(0,test_input.q_id(),sessionUser.user_id());//ID(シリアルなので適当)、問題ID、ユーザー名を保持するflags型の変数
                //service.insertFlags(flags);//flagクラスに問題を追加
            }
            testData.add(question);//回答表示用のリストに採点した問題を格納
        }


        Calendar cl = Calendar.getInstance();/////////////////////////////////////////////////
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");////今日の日付を取得
        String str1 = sdf1.format(cl.getTime());//////////////////////////////////////////////


//        Scores testScore = new Scores(0, sessionUser.user_id(), score,str1);//受験日、ユーザーID、点数等の受験結果を保持する変数
        //service.insertScore(testScore);//受験結果をScoreテーブルに追加
        //service.insertTest_result(result);

        model.addAttribute("testData",testData);
//        model.addAttribute("testScore",testScore);
        model.addAttribute("ans",ans);
        return "result";
    }
}
