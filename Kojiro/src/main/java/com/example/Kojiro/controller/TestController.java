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
import java.util.*;

@Controller
public class TestController {

    @Autowired
    private HttpSession session;

    @Autowired
    private PgQuestionsService pgQuestionsService;


    //テスト表示用
    @GetMapping("test")
    public String test(Model model){
        List<TestQuestion> testData = pgQuestionsService.findTest();//1点問題90問、2点問題5問の配列を取得するfindTestメソッドを呼び出す。
        model.addAttribute("testData",testData);
        //testData.forEach(System.out::println);

        return "test";
    }

    //回答取得用　
    @PostMapping("result")
    public String testResult(@RequestParam Map<String, String> formData,Model model){
//        var data = session.getAttribute("user");//セッションにあるuserタグのデータを取得
//        Users sessionUser = (Users) session.getAttribute("user");//user型の変数にデータを格納

        //Mapで問題IDを取得
        List<Integer> q_list = new ArrayList<>();
        formData.forEach((key, value) -> {
            if (key.startsWith("question_id")) {
                q_list.add(Integer.parseInt(value));
            }
        });

        //Mapでユーザーの回答を取得
        List<Integer> choices = new ArrayList<>();
        formData.forEach((key, value) -> {
            if (key.startsWith("choice")) {
                choices.add(Integer.parseInt(value));
            }
        });


        //テストの入力結果をリストにまとめて格納
        List<TestInput> test = new ArrayList<>();
        for (int i = 0; i<q_list.size(); i++){
            test.add(new TestInput(q_list.get(i), choices.get(i),0 ));
        }


        //テスト回数を判定
        //int times = 1;
        //var resultDB = service.findTestResult();
        //for(test_result test_results : resultDB) {//回答用ループ
        //  if (times != test_results.score_id()){//答えを比較して採点
        //      times += 1;
        //  }
        //}

        int score = 0;//合計得点計算用の変数
        List<Questions> testData = new ArrayList<>();//回答表示用のリスト型配列を作成
        List<Misses> miss = new ArrayList<>();
        List<TestResults> results = new ArrayList<>();

        for(TestInput test_input : test) {//回答用ループ
            Questions question = pgQuestionsService.findQuestion(test_input.q_id());//問題IDからテスト問題を取得
            if (question.answer() == test_input.user_select()){//答えを比較して採点
                results.add(new TestResults(0,test_input.q_id(),test_input.user_select(),1,1,1,0));//回答ページ送信用リストに追加 scoreは正解が１、不正解は0
                score += question.score();//回答と同じの場合のみ点数を加算                                                             //score_idは何回目のテストか（第X回模擬試験）
            }
            else {//回答が間違えていた場合
                results.add(new TestResults(0,test_input.q_id(),test_input.user_select(),1,1,0,0));//回答ページ送信用リストに追加
                Misses misses = new Misses(0,test_input.q_id(),"testuser");//ID(シリアルなので適当)、問題ID、ユーザー名を保持するmisses型の変数
                miss.add(misses);
                //service.insertMisses(misses);//missesクラスに問題を追加
            }
            if (test_input.flag() == 1){//問題にフラグが立てられていた場合
//                Flags flags = new Flags(0,test_input.q_id(),sessionUser.user_id());//ID(シリアルなので適当)、問題ID、ユーザー名を保持するflags型の変数
                //service.insertFlags(flags);//flagクラスに問題を追加
            }
            testData.add(question);//回答表示用のリストに採点した問題を格納
        }


        Calendar cl = Calendar.getInstance();/////////////////////////////////////////////////
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");////今日の日付を取得
        String str1 = sdf1.format(cl.getTime());//////////////////////////////////////////////


        Scores testScore = new Scores(0, "testuser", score,str1);//受験日、ユーザーID、点数等の受験結果を保持する変数
        //service.insertScore(testScore);//受験結果をScoreテーブルに追加
        //service.insertTest_result(result);

        System.out.println(testScore);
        //testData.forEach(System.out::println);
        results.forEach(System.out::println);
        System.out.println();
        miss.forEach(System.out::println);

//        model.addAttribute("testData",results); //回答ページ送信用リストをaddAttribute
//        model.addAttribute("testScore",testScore);　//採点結果表示用の変数addAttribute
        return "menu";
    }
}
