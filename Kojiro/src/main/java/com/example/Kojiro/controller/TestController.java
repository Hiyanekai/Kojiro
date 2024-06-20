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

        List<TestQuestion> testDataP2 = pgQuestionsService.findTestP2();
        model.addAttribute("testDataP2",testDataP2);

        //テスト回数を判定
        int times = 1;
        var resultDB = pgQuestionsService.findTestResult(1);//userIdを渡すことでそのユーザーのテスト履歴を検索
//        resultDB.forEach(System.out::println);
        if(resultDB != null){//テスト履歴が無い場合は1回目
            for(TestResults test_results : resultDB) {//回答用ループ
                if (times == test_results.score_id()) times += 1;//テスト履歴がある場合、最新のカウントにする
            }
        }
        model.addAttribute("times",times);//HTMLに受け渡し
        return "test";
    }

    //回答取得用　
    @PostMapping("result")
    public String testResult(@RequestParam Map<String, String> formData,
                             @RequestParam(name = "times") Integer times,Model model){
//        var data = session.getAttribute("user");//セッションにあるuserタグのデータを取得
//        Users sessionUser = (Users) session.getAttribute("user");//user型の変数にデータを格納

        //Mapで問題IDを取得
        List<Integer> q_list = new ArrayList<>();
        formData.forEach((key, value) -> {
            if (key.startsWith("question_id")) {
                q_list.add(Integer.parseInt(value));
            }
        });

        q_list.forEach(System.out::println);

        //Mapでユーザーの回答を取得
        List<Integer> choices = new ArrayList<>();
        formData.forEach((key, value) -> {
            if (key.startsWith("choice")) {
                choices.add(Integer.parseInt(value));
            }
        });

        //Mapでフラグ立てされた問題を取得
        List<Integer> flagsQ = new ArrayList<>();
        formData.forEach((key, value) -> {
            if (key.startsWith("flag")) {
                flagsQ.add(Integer.parseInt(value));
            }
        });

        //Mapで問題IDを取得(2点問題)
        List<Integer> q_list_P2 = new ArrayList<>();
        formData.forEach((key, value) -> {
            if (key.startsWith("P2question_id")) {
                q_list_P2.add(Integer.parseInt(value));
            }
        });

        //Mapでフラグ立てされた問題を取得(2点問題)
        List<Integer> flagsQ_P2 = new ArrayList<>();
        formData.forEach((key, value) -> {
            if (key.startsWith("P2flag")) {
                flagsQ_P2.add(Integer.parseInt(value));
            }
        });

        //Mapでユーザーの回答を取得(2点問題)
        List<String> p2 = new ArrayList<>();
        formData.forEach((key, value) -> {
            if (key.startsWith("P2choice")) {
                p2.add(value);
            }
        });

        //2点問題の回答を成形（3つの回答を1つの文字列として）
        List<String> userSelectP2 = new ArrayList<>();
        int fu = 0;
        String selectData = "";
        for (String p : p2){
            selectData += p;
            fu+= 1;
            if (fu == 3){
                fu = 0;
                userSelectP2.add(selectData);
                selectData = "";
            }
        }


        //テストの入力結果をリストにまとめて格納
        List<TestInput> test = new ArrayList<>();
        for (int i = 0; i<q_list.size(); i++){
            test.add(new TestInput(q_list.get(i), choices.get(i),0 ));
        }

        //テストの入力結果をリストにまとめて格納(2点問題)
        List<TestInputQ2> testP2 = new ArrayList<>();
        for (int i = 0; i<q_list_P2.size(); i++){
            testP2.add(new TestInputQ2(q_list_P2.get(i), userSelectP2.get(i),0 ));
        }


        int score = 0;//合計得点計算用の変数
        List<Questions> testData = new ArrayList<>();//回答用のリスト型配列を作成
        List<QuestionsP2> testDataP2 = new ArrayList<>();//回答用のリスト型配列を作成（2点問題）
        List<Misses> miss = new ArrayList<>();//ミスした問題を格納するリスト型配列を作成
        List<TestResults> results = new ArrayList<>();
        List<TestResults> resultsP2 = new ArrayList<>();

        for(TestInput test_input : test) {//回答用ループ
            int flag = 0;
            Questions question = pgQuestionsService.findQuestion(test_input.q_id());//問題IDからテスト問題を取得
            for (int q : flagsQ){
                if (q == test_input.q_id()){
                    flag = 1;
//                  Flags flags = new Flags(0,test_input.q_id(),0,sessionUser.user_id());//ID(シリアルなので適当)、問題ID、ユーザー名を保持するflags型の変数
//                  service.insertFlags(flags);//flagクラスに問題を追加
                }
            }
            if (question.answer() == test_input.user_select()){//答えを比較して採点
                results.add(new TestResults(0,test_input.q_id(),String.valueOf(test_input.user_select()),times,15,1,flag));//回答ページ送信用リストに追加 scoreは正解が１、不正解は0
                score += question.score();//回答と同じの場合のみ点数を加算                                                             //score_idは何回目のテストか（第X回模擬試験）
            }
            else {//回答が間違えていた場合
                results.add(new TestResults(0,test_input.q_id(),String.valueOf(test_input.user_select()),times,15,0,flag));//回答ページ送信用リストに追加
                Misses misses = new Misses(0,test_input.q_id(),0,15);//ID(シリアルなので適当)、問題ID、ユーザー名を保持するmisses型の変数
                miss.add(misses);
                //pgQuestionsService.insertMiss(misses);//missesクラスに問題を追加
            }
            testData.add(question);//回答表示用のリストに採点した問題を格納
        }

        for(TestInputQ2 test_input : testP2) {//回答用ループ(2点問題)
            int flag = 0;
            QuestionsP2 question = pgQuestionsService.findQuestionP2(test_input.q_id());//問題IDからテスト問題を取得 テーブル違うからDao追加
            for (int q : flagsQ_P2){
                if (q == test_input.q_id()){
                    flag = 1;
//                  Flags flags = new Flags(0,0,test_input.q_id(),sessionUser.user_id());//ID(シリアルなので適当)、問題ID、ユーザー名を保持するflags型の変数
//                  service.insertFlags(flags);//flagクラスに問題を追加
                }
            }
            if (question.answer().equals(test_input.user_select())){//答えを比較して採点
                resultsP2.add(new TestResults(0,test_input.q_id(),test_input.user_select(),times,15,2,flag));//回答ページ送信用リストに追加 scoreは正解が１、不正解は0
                score += question.score();//回答と同じの場合のみ点数を加算                                                             //score_idは何回目のテストか（第X回模擬試験）
            }
            else {//回答が間違えていた場合
                resultsP2.add(new TestResults(0,test_input.q_id(),test_input.user_select(),times,15,0,flag));//回答ページ送信用リストに追加
                Misses misses = new Misses(0,0,test_input.q_id(),15);//ID(シリアルなので適当)、問題ID、ユーザー名を保持するmisses型の変数
                miss.add(misses);
                //pgQuestionsService.insertMiss(misses);//missesクラスに問題を追加
            }
            testDataP2.add(question);//回答表示用のリストに採点した問題を格納
        }


        Calendar cl = Calendar.getInstance();/////////////////////////////////////////////////
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");////今日の日付を取得
        String str1 = sdf1.format(cl.getTime());//////////////////////////////////////////////


        Scores testScore = new Scores(0, 15, score, str1);//受験日、ユーザーID、点数等の受験結果を保持する変数
        //service.insertScore(testScore);//受験結果をScoreテーブルに追加
        //service.insertTest_result(result);

        System.out.println(testScore);
        System.out.println();
        //testData.forEach(System.out::println);
        results.forEach(System.out::println);
        System.out.println();
        miss.forEach(System.out::println);
        model.addAttribute("times",times);//HTMLに受け渡し
        model.addAttribute("result",results);
        model.addAttribute("resultP2",resultsP2);
        model.addAttribute("testData",testData); //回答ページ送信用リストをaddAttribute
        model.addAttribute("testDataP2",testDataP2); //回答ページ送信用リストをaddAttribute
//        model.addAttribute("testScore",testScore);　//採点結果表示用の変数addAttribute
        return "result";
    }
}
