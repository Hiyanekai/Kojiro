package com.example.Kojiro.controller;

import com.example.Kojiro.entity.TermManagement;
import com.example.Kojiro.entity.UserManagement;
import com.example.Kojiro.service.TermManagementService;
import com.example.Kojiro.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TermManagementController {
    @Autowired
    TermManagementService TermManagementService;

    @GetMapping("/term-detail/{id}")
    public String termdeta(@PathVariable int id, Model model){
        TermManagement detail = TermManagementService.findById(id);
        model.addAttribute("detail", detail);
        return "term-detail";
    }
}
