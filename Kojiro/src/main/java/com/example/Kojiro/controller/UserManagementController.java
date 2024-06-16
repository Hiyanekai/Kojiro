package com.example.Kojiro.controller;

import com.example.Kojiro.entity.UserManagement;
import com.example.Kojiro.service.PgUserManagementService;
import com.example.Kojiro.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserManagementController {
    @Autowired
    UserManagementService UserManagementService;


    @GetMapping("/user-management")
    public String userList(Model model) {
        model.addAttribute("users", UserManagementService.findAll());
        return "user-management";
    }

    @RequestMapping(value="/user-management", params="user_id")
    public String menu(@RequestParam(value = "user_id", defaultValue = "") @PathVariable String user_id, Model model) {
        if (user_id != null && !user_id.trim().isEmpty()) {
            model.addAttribute("users", UserManagementService.search(user_id));
        } else {
            model.addAttribute("users", UserManagementService.findAll());
        }
        return "user-management";
    }

    @GetMapping("/user-detail/{id}")
    public String userdeta(@PathVariable int id, Model model){
        UserManagement detail = UserManagementService.findById(id);
        model.addAttribute("detail", detail);
        return "user-detail";
    }

    @PostMapping("/user-detail")
    public String productDelete(@ModelAttribute("detail") UserManagement delete) {
        UserManagementService.delete(delete.id());
        return "success";
    }

    @GetMapping("/user-update/{id}")
    public String update(@PathVariable int id, Model model, @ModelAttribute("update") UserManagement change){
        UserManagement update = UserManagementService.findById(id);
        model.addAttribute("update", update);
        return "user-update";
    }

    @PostMapping("/update")
    public String change(@Validated @ModelAttribute("update") UserManagement change, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            return "user-update";
        }
        UserManagement name = UserManagementService.findByUserId(change.user_id());
        if (name != null && !(name.id().equals(change.id()))) {
            model.addAttribute("errorMsg", "ユーザーIDが重複");
            return "user-update";
        }
        UserManagementService.update(change);
        return "success";
    }
}
