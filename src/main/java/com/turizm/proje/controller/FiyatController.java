package com.turizm.proje.controller;

import com.turizm.proje.repository.FiyatDonemiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FiyatController {

    @Autowired private FiyatDonemiRepository fiyatRepository;

    @GetMapping("/kampanyalar")
    public String kampanyalar(Model model) {
        model.addAttribute("kampanyaListesi", fiyatRepository.findAll());
        return "kampanyalar";
    }
}