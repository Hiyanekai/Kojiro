package com.example.Kojiro.controller;

import com.example.Kojiro.service.QManagementService;
import com.example.Kojiro.service.TermService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

@Controller
public class TermController {

    @Autowired
    TermService termService;
    @Autowired
    private HttpSession session;

    @GetMapping("/word")
    public String menu(@RequestParam(name="keyword" ,defaultValue = "") String keyword, Model model) {
//        if (session.getAttribute("user")==null){//ユーザーのセッション判定
//            return "redirect:/login-test";
//        }
        if (keyword.isEmpty()){//検索欄にキーワードなしは全部出す
            model.addAttribute("termlist", termService.findAll());
        }else {//検索欄にキーワード入れると問題文から抽出
            System.out.println(termService.findByTerm(keyword));
            model.addAttribute("termlist", termService.findByTerm(keyword));
        }
        return "term";
    }

    @GetMapping("terms-detail/{id}")//id指定で詳細のページを出す
    public String detail(@PathVariable("id") int id, Model model) {
        System.out.println(id);
        System.out.println(termService.findById(id));
        var q = termService.findById(id);
        model.addAttribute("term",q);
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
        return "term-explain";
    }
}
