package com.turizm.proje.controller;

import com.turizm.proje.repository.UlkeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UlkeController {

    @Autowired
    private UlkeRepository ulkeRepository;

    // DESTİNASYONLAR SAYFASI
    @GetMapping("/ulkeler")
    public String ulkeler(Model model) {
        model.addAttribute("ulkeListesi", ulkeRepository.findAll());
        return "ulkeler";
    }
}