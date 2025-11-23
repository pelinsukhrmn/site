package com.turizm.proje.controller;

import com.turizm.proje.entity.Otel;
import com.turizm.proje.repository.OtelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/oteller")
public class OtelController {

    @Autowired
    private OtelRepository otelRepository;

    @GetMapping
    public String listele(Model model) {
        // HTML'de "otelListesi" diye çağırıyoruz, buradaki isimle aynı olmalı!
        model.addAttribute("otelListesi", otelRepository.findAll());
        return "oteller";
    }

    @GetMapping("/ekle")
    public String ekleFormu(Model model) {
        model.addAttribute("yeniOtel", new Otel());
        return "otel-ekle";
    }

    @PostMapping("/kaydet")
    public String kaydet(@ModelAttribute Otel otel) {
        otelRepository.save(otel);
        return "redirect:/oteller";
    }

    @GetMapping("/sil/{id}")
    public String sil(@PathVariable Long id) {
        otelRepository.deleteById(id);
        return "redirect:/oteller";
    }
}