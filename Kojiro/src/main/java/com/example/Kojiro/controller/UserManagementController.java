package com.example.Kojiro.controller;

import com.example.Kojiro.entity.UserManagement;
import com.example.Kojiro.entity.Users;
import com.example.Kojiro.service.UserManagementService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpSession session;

    @GetMapping("/user-management")
    public String userList(Model model) {
        if(request.getSession(false)==null) return "redirect:/index";
        var user = (Users)session.getAttribute("users");
        if(user.role() != 1) return "redirect:/menu";
        model.addAttribute("users", UserManagementService.findAll());
        return "user-management";
    }

    @RequestMapping(value="/user-management", params="user_id")
    public String userMenu(@RequestParam(value = "user_id", defaultValue = "") @PathVariable String user_id, Model model) {
        if (user_id != null && !user_id.trim().isEmpty()) {
            model.addAttribute("users", UserManagementService.search(user_id));
        } else {
            model.addAttribute("users", UserManagementService.findAll());
        }
        return "user-management";
    }

    @GetMapping("/user-detail/{id}")
    public String userDetail(@PathVariable int id, Model model){
        if(request.getSession(false)==null) return "redirect:/index";
        var user = (Users)session.getAttribute("users");
        if(user.role() != 1) return "redirect:/menu";
        UserManagement detail = UserManagementService.findById(id);
        model.addAttribute("detail", detail);
        return "user-detail";
    }

    @PostMapping("/user-detail")
    public String userDelete(@ModelAttribute("detail") UserManagement delete) {
        UserManagementService.delete(delete.id());
        return "user-success";
    }

    @GetMapping("/user-update/{id}")
    public String userUpdate(@PathVariable int id, Model model, @ModelAttribute("update") UserManagement change){
        if(request.getSession(false)==null) return "redirect:/index";
        var user = (Users)session.getAttribute("users");
        if(user.role() != 1) return "redirect:/menu";
        UserManagement update = UserManagementService.findById(id);
        model.addAttribute("update", update);
        return "user-update";
    }

    @PostMapping("/user-update")
    public String userChange(@Validated @ModelAttribute("update") UserManagement change, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            return "user-update";
        }
        UserManagement name = UserManagementService.findByUserId(change.user_id());
        if (name != null && !(name.id().equals(change.id()))) {
            model.addAttribute("errorMsg", "ユーザーIDが重複");
            return "user-update";
        }
        UserManagementService.update(change);
        return "user-success";
    }
}
