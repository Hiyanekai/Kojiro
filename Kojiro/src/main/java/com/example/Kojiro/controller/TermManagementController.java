package com.example.Kojiro.controller;

import com.example.Kojiro.entity.TermManagement;
import com.example.Kojiro.entity.TermManagementNotFile;
import com.example.Kojiro.form.TermUpdateForm;
import com.example.Kojiro.service.TermManagementService;
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
public class TermManagementController {
    @Autowired
    TermManagementService TermManagementService;

    @GetMapping("/term-detail/{id}")
    public String termDetail(@PathVariable int id, Model model){
        TermManagement detail = TermManagementService.findById(id);
        var genres = TermManagementService.findByGenres(detail.genre_id());
        model.addAttribute("genres", genres);
        model.addAttribute("detail", detail);
        // データベースにファイルパスがない場合、画像を表示しない
        if(detail.file()!=null && !detail.file().isEmpty()) {
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
    public String termDelete(@ModelAttribute("detail") TermManagement delete) {
        TermManagementService.delete(delete.id());
        return "term-success";
    }
    @GetMapping("/term-update/{id}")
    public String termUpdate(@PathVariable int id, @ModelAttribute("update")  TermManagement termManagement, Model model){
        TermManagement update = TermManagementService.findById(id);
        model.addAttribute("update", update);
        model.addAttribute("genresList", TermManagementService.findAll());
        return "term-update";
    }

//    @PostMapping("/term-update/{id}")
//    public String termChange(@PathVariable int id, @Validated @ModelAttribute("update") TermManagement changes, BindingResult bindingResult, Model model){
//
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("genresList", TermManagementService.findAll());
//            return "term-update";
//        }
//        TermManagement update = TermManagementService.findByTermId(changes.term_name());
//        if (update != null && !(update.id().equals(changes.id()))) {
//            model.addAttribute("errorMsg", "用語名が重複");
//            model.addAttribute("genresList", TermManagementService.findAll());
//            return "term-update";
//        }
//        TermManagementService.update(changes);
//        return "term-success";
//
//    }

    @PostMapping("/term-update/{id}")
    public String termChange(@PathVariable int id, @Validated @ModelAttribute("update") TermUpdateForm changes, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()) {
            model.addAttribute("genresList", TermManagementService.findAll());
            return "/term-update";
        }
        TermManagement update = TermManagementService.findByTermId(changes.getTerm_name());
        if (update != null && !(update.id().equals(changes.getId()))) {
            model.addAttribute("errorMsg", "用語名が重複");
            model.addAttribute("genresList", TermManagementService.findAll());
            return "/term-update";
        }
        if (changes.getFile().getOriginalFilename().equals("")){
            TermManagementService.updates(new TermManagementNotFile(changes.getId(), changes.getGenre_id(), changes.getTerm_name(), changes.getExplain()));
        }else {
            TermManagementService.update(new TermManagement(changes.getId(), changes.getGenre_id(), changes.getTerm_name(), changes.getExplain(), changes.getFile().getOriginalFilename()));
        }
        insertImgFile(changes.getFile());

        return "term-success";

    }

//        @RequestMapping(value="/term-update",method=RequestMethod.POST, params = "term-update")
//        public void insertFile(@RequestParam(value = "file", defaultValue = "") MultipartFile file) {
//            final String UPLOAD_DIR = "./Kojiro/src/main/resources/static/images/";
//
//            try {
//                if (!file.getOriginalFilename().equals("")) {
//                    String filePath = UPLOAD_DIR + File.separator + file.getOriginalFilename();
//                    Path destination = new File(filePath).toPath();
//                    Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    public  void insertImgFile(MultipartFile file){
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

//    @PostMapping("/term-update")
//    public String insertFile(@ModelAttribute("update") TermUpdateForm termUpdateForm,  BindingResult bindingResult,  Model model) {
//        final String UPLOAD_DIR = "./Kojiro/src/main/resources/static/images/";
//        System.out.println("ファイル名：" + termUpdateForm.getFile().getOriginalFilename());
//        try {
//            if (!termUpdateForm.getFile().getOriginalFilename().equals("")) {
//
//                String filePath = UPLOAD_DIR + File.separator + termUpdateForm.getFile().getOriginalFilename();
//                Path destination = new File(filePath).toPath();
//                Files.copy(termUpdateForm.getFile().getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
//                return "/term-success";
//            } else {
//                return "/term-update";
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "redirect:/term-detail/2";
//    }

}
