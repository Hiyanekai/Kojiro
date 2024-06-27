package com.example.Kojiro.controller;

import com.example.Kojiro.entity.TermManagementDetail;
import com.example.Kojiro.entity.TermManagementNotFile;
import com.example.Kojiro.entity.Users;
import com.example.Kojiro.form.TermUpdateForm;
import com.example.Kojiro.service.TermManagementDetailService;
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
public class TermManagementDetailController {
    @Autowired
    TermManagementDetailService TermManagementService;
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpSession session;


    @GetMapping("/term-detail/{id}")
    public String termDetail(@PathVariable int id, Model model) {
        if(request.getSession(false)==null) return "redirect:/index";
        var user = (Users)session.getAttribute("users");
        if(user.role() != 1) return "redirect:/menu";
        TermManagementDetail detail = TermManagementService.findById(id);
//        var genres = TermManagementService.findByGenres(detail.genre_id());
//        model.addAttribute("genres", genres);
        model.addAttribute("detail", detail);
        // データベースにファイルパスがない場合、画像を表示しない
        if (detail.file() != null && !detail.file().isEmpty()) {
            File img = new File("./Kojiro/src/main/resources/static/images/" + detail.file());
            System.out.println(img.toPath());
            try {
                byte[] byteImg = Files.readAllBytes(img.toPath());
                String base64Data = Base64.getEncoder().encodeToString(byteImg);
                model.addAttribute("base64Data", "data:img/png;base64," + base64Data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "term-detail";
    }

    @PostMapping("/term-detail")
    public String termDelete(@ModelAttribute("detail") TermManagementDetail delete) {
        TermManagementService.delete(delete.id());
        return "term-success";
    }

    @GetMapping("/term-update/{id}")
    public String termUpdate(@PathVariable int id, @ModelAttribute("update") TermManagementDetail termManagement, Model model) {
        if(request.getSession(false)==null) return "redirect:/index";
        var user = (Users)session.getAttribute("users");
        if(user.role() != 1) return "redirect:/menu";
        TermManagementDetail update = TermManagementService.findById(id);
        model.addAttribute("update", update);
//        model.addAttribute("genresList", TermManagementService.findAll());
        return "term-update";
    }


    @PostMapping("/term-update/{id}")
    public String termChange(@PathVariable int id, @Validated @ModelAttribute("update") TermUpdateForm changes, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "/term-update";
        }
        TermManagementDetail update = TermManagementService.findByTermId(changes.getTerm_name());
        if (update != null && !(update.id().equals(changes.getId()))) {
            model.addAttribute("errorMsg", "用語名が重複");
            return "/term-update";
        }
        if (changes.getFile().getOriginalFilename().equals("")) {
            TermManagementService.updates(new TermManagementNotFile(changes.getId(), changes.getTerm_name(), changes.getExplain()));
        } else {
            TermManagementService.update(new TermManagementDetail(changes.getId(), changes.getTerm_name(), changes.getExplain(), changes.getFile().getOriginalFilename()));
        }
        insertImgFile(changes.getFile());

        return "term-success";

    }

    public void insertImgFile(MultipartFile file) {
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
