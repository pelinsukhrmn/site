package com.turizm.proje.controller;

import com.turizm.proje.entity.Otel;
import com.turizm.proje.repository.OtelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/oteller")
public class OtelController {

    @Autowired
    private OtelRepository otelRepository;

    // 1. LİSTELEME
    @GetMapping
    public String listele(Model model) {
        model.addAttribute("otelListesi", otelRepository.findAll());
        return "oteller";
    }

    // 2. EKLEME SAYFASI
    @GetMapping("/ekle")
    public String ekleFormu(Model model) {
        model.addAttribute("yeniOtel", new Otel());
        return "otel-ekle";
    }

    // 3. KAYDET
    @PostMapping("/kaydet")
    public String kaydet(@ModelAttribute Otel otel) {
        // Resim URL boşsa varsayılan ata (Hata vermesin)
        if (otel.getResimUrl() == null || otel.getResimUrl().isEmpty()) {
            otel.setResimUrl(null);
        }
        otelRepository.save(otel);
        return "redirect:/oteller";
    }

    // 4. SİLME (Güvenli)
    @GetMapping("/sil/{id}")
    public String sil(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            otelRepository.deleteById(id);
        } catch (Exception e) {
            // Eğer otele bağlı kayıt varsa silinmez, hata vermesin diye yakalıyoruz
            System.out.println("HATA: Otel silinemedi (Bağlı kayıtlar olabilir).");
        }
        return "redirect:/oteller";
    }
}