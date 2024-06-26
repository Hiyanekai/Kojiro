package com.example.Kojiro.controller;

import com.example.Kojiro.entity.*;
import com.example.Kojiro.service.PgTermManagementService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Controller
public class TermManagementController {
    @Autowired
    private HttpSession session;
    @Autowired
    private PgTermManagementService pgTermManagementService;
    @Autowired
    private HttpServletRequest request;

//    @GetMapping("/terms")
////    public String TermManagement(@ModelAttribute("termslist") Model model ) {
//        return "termmanagement";
//    }

    @GetMapping("/term")
    public String TermManagement2(@RequestParam(name = "keyword", defaultValue = "") String keyword, Model model) {
        if(request.getSession(false)==null) return "redirect:/index";
        var user = (Users)session.getAttribute("users");
        if(user.role() != 1) return "redirect:/menu";
        if (keyword.isEmpty()) {
            model.addAttribute("termlist", pgTermManagementService.findAll());
        } else {
            model.addAttribute("termlist", pgTermManagementService.findByTerm(keyword));
        }
        return "termmanagement";
    }

    @GetMapping("/termAddition")
    public String TermAddition(Model model, @ModelAttribute("termaddition") TermForm termForm) {
//        if(request.getSession(false)==null) return "redirect:/index";
        return "termAddition";
    }

    @PostMapping("/termAddition")
    public String TermAddition2(@Validated @ModelAttribute("termaddition") TermForm termForm, BindingResult bindingResult, Model model) {
        if(request.getSession(false)==null) return "redirect:/index";
        var user = (Users)session.getAttribute("users");
        if(user.role() != 1) return "redirect:/menu";
        var result2 = pgTermManagementService.findtermAddition(termForm.getTerm_name());
        if (bindingResult.hasErrors()) {
            return "termAddition";
        }else if (result2 == null ){
            pgTermManagementService.termAddition(new TermAddition(termForm.getTerm_name(), termForm.getExplain(), termForm.getFile().getOriginalFilename()));
            insertImgFiles(termForm.getFile());
            return "redirect:/term";
        }else {
            model.addAttribute("errorsyori", "商品コードが重複しています");
            return "termAddition";
        }
    }

    public void insertImgFiles(MultipartFile file) {
//        if(request.getSession(false)==null) return "redirect:/index";
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
