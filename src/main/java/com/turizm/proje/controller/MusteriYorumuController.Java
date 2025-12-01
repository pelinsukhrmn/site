package com.turizm.proje.controller;

import com.turizm.proje.repository.MusteriYorumuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class YorumController {

    @Autowired private MusteriYorumuRepository yorumRepository;

    @GetMapping("/yorumlar")
    public String yorumlar(Model model) {
        model.addAttribute("yorumListesi", yorumRepository.findAll());
        return "yorumlar";
    }
}