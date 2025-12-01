package com.turizm.proje.controller;

import com.turizm.proje.entity.FiyatDonemi;
import com.turizm.proje.repository.FiyatDonemiRepository;
import com.turizm.proje.repository.TurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/kampanyalar")
public class FiyatController {

    @Autowired private FiyatDonemiRepository fiyatRepository;
    @Autowired private TurRepository turRepository;

    // 1. KAMPANYA LİSTESİ (Vitrin)
    @GetMapping
    public String kampanyalar(Model model) {
        model.addAttribute("kampanyaListesi", fiyatRepository.findAll());
        return "kampanyalar";
    }

    // 2. KAMPANYA EKLEME SAYFASI
    @GetMapping("/ekle")
    public String ekleFormu(Model model) {
        model.addAttribute("yeniKampanya", new FiyatDonemi());
        // Hangi tura indirim yapacağız? Dropdown için turları gönderiyoruz
        model.addAttribute("turlar", turRepository.findAll());
        return "kampanya-ekle";
    }

    // 3. KAYDET
    @PostMapping("/kaydet")
    public String kaydet(@ModelAttribute FiyatDonemi kampanya) {
        fiyatRepository.save(kampanya);
        return "redirect:/kampanyalar";
    }
}