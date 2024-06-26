package com.example.Kojiro.controller;

//QManagementController = QuestionManagementController(問題管理) 長くてすみません

import com.example.Kojiro.dao.GenresDao;
import com.example.Kojiro.entity.*;
import com.example.Kojiro.form.QuizManagement;
import com.example.Kojiro.form.QuizManagementStrAns;
import com.example.Kojiro.service.QManagementService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Base64;

@Controller
public class QManagementController {

    private String[] successMesList={"","追加に成功","更新に成功","削除に成功"};

    private int successIndex=0;
    @Autowired
    QManagementService qManagementService;
    @Autowired
    private GenresDao genresDao;
    @Autowired
    private HttpSession session;
    @Autowired
    private HttpServletRequest request;

    @GetMapping("/quiz-management")
    public String menu(@RequestParam(name="keyword" ,defaultValue = "") String keyword, Model model) {
        if(request.getSession(false)==null) return "redirect:/index";
        var user = (Users)session.getAttribute("users");
        if(user.role() != 1) return "redirect:/menu";
        if (keyword.isEmpty()){//検索欄にキーワードなしは全部出す
//            model.addAttribute("management", qManagementService.findAll());
            model.addAttribute("management", qManagementService.findAll2());
            model.addAttribute("success",successMesList[successIndex]);
        }else {//検索欄にキーワード入れると問題文から抽出
            System.out.println(qManagementService.findBySentence2(keyword));
            model.addAttribute("management", qManagementService.findBySentence2(keyword));
        }
        return "quiz-management";
    }

//    @GetMapping("quiz-detail/{id}")//id指定で詳細のページを出す
//    public String detail(@PathVariable("id") int id, Model model) {
//        System.out.println(id);
//        System.out.println(qManagementService.findById(id));
//        var q = qManagementService.findById(id);
//        model.addAttribute("q_management",q);
//        if(q.file()!=null && !q.file().equals("")) {
//            File img = new File("./Kojiro/src/main/resources/static/images/" + q.file());
//            try {
//                byte[] byteImg = Files.readAllBytes(img.toPath());
//                String base64Data = Base64.getEncoder().encodeToString(byteImg);
//                model.addAttribute("base64Data", "data:img/png;base64," + base64Data);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return "quiz-detail";
//    }

    @GetMapping("quiz-detail/{id}/{gName}")//id指定で詳細のページを出す
    public String detail(@PathVariable("id") int id,
                         @PathVariable("gName") String gName, Model model) {
        if(request.getSession(false)==null) return "redirect:/index";
        var user = (Users)session.getAttribute("users");
        if(user.role() != 1) return "redirect:/menu";
        System.out.println(id);
        if(gName.equals("危険予測ディスカッション")){
            var result = qManagementService.findById2(id);
            model.addAttribute("q_management",result);
            showImage(result.file(), model);
        } else {
            System.out.println(qManagementService.findById(id));
            var result = qManagementService.findById(id);
            model.addAttribute("q_management", result);
            showImage(result.file(), model);
        }
        return "quiz-detail";
    }

//    @GetMapping("quiz-update/{id}")//id指定で更新のページを出す
//    public String update1(@PathVariable("id") int id, Model model) {
//        System.out.println(id);
//        System.out.println(qManagementService.findById(id));
//        model.addAttribute("q_management", qManagementService.findById(id));
//        return "quiz-update";
//    }
    @GetMapping("quiz-update/{id}/{genre}")//id指定で更新のページを出す
    public String update1(@PathVariable("id") int id,
                          @PathVariable("genre") String gName, Model model) {
//        if(request.getSession(false)==null) return "redirect:/index";
        System.out.println(id);
        var genres = genresDao.findAll();
        if(gName.equals("危険予測ディスカッション")){
            var q = qManagementService.findById2(id);
            for(var genre : genres){
                if(genre.genre_name().equals(q.genre())){
                    q = new TestQuestionP2(q.id(), String.valueOf(genre.id()), q.sentence(), q.answer(), q.explain(), q.file(), q.score());
                    break;
                }
            }
            model.addAttribute("q_management", q);
            System.out.println(q.answer());
        } else {
            System.out.println(qManagementService.findById(id));
            var q = qManagementService.findById(id);
            for(var genre : genres){
                if(genre.genre_name().equals(q.genre())){
                    q = new TestQuestion(q.id(), String.valueOf(genre.id()), q.sentence(), q.answer(), q.explain(), q.file(), q.score());
                    break;
                }
            }
            model.addAttribute("q_management", q);
        }
        return "quiz-update";
    }

//    @PostMapping("quiz-update/{id}")
//    public String update2(@PathVariable("id") int id, @Validated @ModelAttribute("q_management") QuizManagement update, BindingResult bindingResult, Model model){
//        if(bindingResult.hasErrors()) {//バリデーションチェック
//            return "quiz-update";
//        }else {
//            System.out.println(update);
//            var conProduct1 = new TestQuestion(id,update.getGenre(),update.getSentence(),update.getAnswer(),update.getExplain(),update.getFile(),update.getScore());
//            try{//正常に更新したとき
//                var result2 = qManagementService.update(conProduct1);
//                System.out.println(conProduct1);
//                successIndex=2;
//                return "redirect:/quiz-management";
//            }catch (RuntimeException e){//id(主キー)が重複したときエラーコードを出して、更新のページに戻す
//                model.addAttribute("q_management", qManagementService.findById(id));
//                System.out.println("重複しましたよ");
//                return "quiz-update";
//            }
//        }
//    }
    @PostMapping("quiz-update/{id}/{genre}")
    public String update2(@PathVariable("id") int id, @PathVariable("genre") String gName, @Validated @ModelAttribute("q_management") QuizManagementStrAns update, BindingResult bindingResult, Model model){
        if(request.getSession(false)==null) return "redirect:/index";
        var user = (Users)session.getAttribute("users");
        if(user.role() != 1) return "redirect:/menu";
        if(bindingResult.hasErrors()) {//バリデーションチェック
            return "quiz-update";
        }else {
            // サーバーに画像を追加
            insertImgFiles(update.getFile());
            if(gName.equals("危険予測ディスカッション")){
                System.out.println(update);
                var ansText = String.valueOf(update.getAnswer());
                if(ansText.length() == 1) ansText="00"+ansText;
                else if(ansText.length() == 2) ansText="0"+ansText;
                var conProduct1 = new TestQuestionP2(id, update.getGenre(), update.getSentence(), ansText, update.getExplain(), update.getFile().getOriginalFilename(), update.getScore());
                try {//正常に更新したとき
                    var result2 = qManagementService.update(conProduct1);
                    System.out.println(conProduct1);
                    successIndex = 2;
                    return "redirect:/quiz-management";
                } catch (RuntimeException e) {//id(主キー)が重複したときエラーコードを出して、更新のページに戻す
                    model.addAttribute("q_management", qManagementService.findById(id));
                    System.out.println("重複しましたよ");
                    return "quiz-update";
                }
            } else {
                System.out.println(update);
                var conProduct1 = new TestQuestion(id, update.getGenre(), update.getSentence(), update.getAnswer(), update.getExplain(), update.getFile().getOriginalFilename(), update.getScore());
                try {//正常に更新したとき
                    qManagementService.update(conProduct1);
                    System.out.println(conProduct1);
                    successIndex = 2;
                    return "redirect:/quiz-management";
                } catch (RuntimeException e) {//id(主キー)が重複したときエラーコードを出して、更新のページに戻す
                    model.addAttribute("q_management", qManagementService.findById(id));
                    System.out.println("重複しましたよ");
                    return "quiz-update";
                }
            }
        }
    }
//    @GetMapping("/quiz-delete/{id}")//問題の削除(idを指定)
//    public String delete1(@PathVariable("id") int id){
//        var result3 = qManagementService.delete(id);
//        successIndex=3;
//        return "redirect:/quiz-management";
//    }
    @GetMapping("/quiz-delete/{id}/{genre}")//問題の削除(idを指定)
    public String delete1(@PathVariable("id") int id, @PathVariable("genre") String gName){
        if(request.getSession(false)==null) return "redirect:/index";
        var user = (Users)session.getAttribute("users");
        if(user.role() != 1) return "redirect:/menu";
        if(gName.equals("危険予測ディスカッション")){
            var result = qManagementService.delete2(id);
            successIndex=3;
        } else {
            var result3 = qManagementService.delete(id);
            successIndex = 3;
        }
        return "redirect:/quiz-management";
    }

    @GetMapping("/quiz-add")
    public String index(@ModelAttribute("add") QuizManagement add) {
        return "quiz-add";
    }
//    @PostMapping("/quiz-add")
//    public String product1(@Validated @ModelAttribute("add") QuizManagement add, BindingResult bindingResult) {
//        System.out.println(add);
//        if(bindingResult.hasErrors()) {//バリデーションチェック
//            System.out.println("バリデーション");
//            return "quiz-add";
//        }else {
//            System.out.println("バリデーションなし");
//            var conProduct = new TestQuestion(0,add.getGenre(),add.getSentence(),add.getAnswer(),add.getExplain(),add.getFile(),add.getScore());
//            try{//正常に問題追加したとき
//                var result1 = qManagementService.insert(conProduct);
//                System.out.println(conProduct);
//                successIndex=1;
//                return "redirect:/quiz-management";
//            }catch (RuntimeException e){//id(主キー)が重複したときエラーコードを出して、追加のページに戻す
//                System.out.println("重複しましたよ");
//                return "quiz-add";
//            }
//        }
//    }
    @PostMapping("/quiz-add")
    public String product1(@Validated @ModelAttribute("add") QuizManagementStrAns add, BindingResult bindingResult) {
        if(request.getSession(false)==null) return "redirect:/index";
        var user = (Users)session.getAttribute("users");
        if(user.role() != 1) return "redirect:/menu";
        System.out.println(add);
        if (bindingResult.hasErrors()) {
            System.out.println("バリデーション");
            return "quiz-add";
        } else {
            // 画像追加
            insertImgFiles(add.getFile());
            try {
                System.out.println("バリデーションなし");

                if (add.getGenre().equals("30")) {
                    var ansText = String.valueOf(add.getAnswer());
                    if(ansText.length() == 1) ansText="00"+ansText;
                    else if(ansText.length() == 2) ansText="0"+ansText;
                    System.out.println("アンサー：" + ansText);
                    qManagementService.insert(new Questions2points(-1, Integer.valueOf(add.getGenre()), add.getSentence(), ansText, add.getExplain(), add.getFile().getOriginalFilename(), add.getScore()));
                } else {
                    var conProduct = new TestQuestion(0, add.getGenre(), add.getSentence(), add.getAnswer(), add.getExplain(), add.getFile().getOriginalFilename(), add.getScore());
                    qManagementService.insert(conProduct);
                }
                successIndex = 1;
                return "redirect:/quiz-management";
            } catch (RuntimeException e) {
                System.out.println("重複しましたよ");
                return "quiz-add";
            }
        }
    }


    /* 画像関連メソッド */
    // 画像表示
    public void showImage(String fName, Model model){
        if(fName!=null && !fName.equals("")) {
            File img = new File("./Kojiro/src/main/resources/static/images/" + fName);
            try {
                byte[] byteImg = Files.readAllBytes(img.toPath());
                String base64Data = Base64.getEncoder().encodeToString(byteImg);
                model.addAttribute("base64Data", "data:img/png;base64," + base64Data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    // サーバーに画像を保存
    public void insertImgFiles(MultipartFile file) {
        final String UPLOAD_DIR = "./Kojiro/src/main/resources/static/images/";
        try {
            if (!file.getOriginalFilename().equals("")) {
                String filePath = UPLOAD_DIR + File.separator + file.getOriginalFilename();
                Path destination = new File(filePath).toPath();
                Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
