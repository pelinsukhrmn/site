package com.turizm.proje.controller;

import com.turizm.proje.repository.UlkeRepository;
import com.turizm.proje.repository.CalisanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UlkeController {

    @Autowired private UlkeRepository ulkeRepository;
    @Autowired private CalisanRepository calisanRepository;

    @GetMapping("/ulkeler")
    public String ulkeler(Model model) {
        model.addAttribute("ulkeListesi", ulkeRepository.findAll());
        return "ulkeler";
    }

    @GetMapping("/rehberler")
    public String rehberler(Model model) {
        model.addAttribute("rehberListesi", calisanRepository.findAll());
        return "rehberler";
    }
}