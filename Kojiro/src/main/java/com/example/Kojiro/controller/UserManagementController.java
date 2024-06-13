package com.example.Kojiro.controller;

import com.example.Kojiro.entity.UserManagement;
import com.example.Kojiro.service.PgUserManagementService;
import com.example.Kojiro.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserManagementController {
    @Autowired
    UserManagementService UserManagementService;


    @GetMapping("/user-management")
    public String userList(Model model) {
        model.addAttribute("users", UserManagementService.findAll());
        return "user-management";
    }

    @GetMapping("/user-detail/{id}")
    public String userdeta(@PathVariable int id, Model model){
        UserManagement detail = UserManagementService.findById(id);
        model.addAttribute("detail", detail);
        return "user-detail";
    }

    @GetMapping("/user-update/{id}")
    public String update(@PathVariable int id, Model model, @ModelAttribute("update") UserManagement change){
        UserManagement update = UserManagementService.findById(id);
        model.addAttribute("update", update);
        return "user-update";
    }

    @PostMapping("/user-update")
    public String change(@ModelAttribute("update") UserManagement change,Model model){
        UserManagementService.update(change);
        return "user-management";
    }
}
