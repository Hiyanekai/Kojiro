package com.example.Kojiro.controller;

import com.example.Kojiro.service.QManagementService;
import com.example.Kojiro.service.TermService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TermController {

    @Autowired
    TermService termService;
    @Autowired
    private HttpSession session;

    @GetMapping("/term")
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
}
