package com.example.Kojiro.controller;

import com.example.Kojiro.entity.TermManagement;
import com.example.Kojiro.service.PgTermManagementService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TermManagementController {
    @Autowired
    private HttpSession session;
    @Autowired
    private PgTermManagementService pgTermManagementService;

    @GetMapping("/terms")
    public String TermManagement(@RequestParam(name = "keyword", defaultValue = "") String keyword, Model model) {
        if (keyword.isEmpty()) {
            model.addAttribute("termslist", pgTermManagementService.findAll());
        } else {
            model.addAttribute("termslist", pgTermManagementService.findByTerm(keyword));
        }
        return "termmanagement";
    }
}
