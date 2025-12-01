package com.turizm.proje.controller;

import com.turizm.proje.entity.Odeme;
import com.turizm.proje.entity.Rezervasyon;
import com.turizm.proje.repository.OdemeRepository;
import com.turizm.proje.repository.RezervasyonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/odemeler")
public class OdemeController {

    @Autowired private OdemeRepository odemeRepository;
    @Autowired private RezervasyonRepository rezervasyonRepository;

    // 1. LİSTELEME
    @GetMapping
    public String listele(Model model) {
        model.addAttribute("odemeListesi", odemeRepository.findAll());
        return "odemeler";
    }

    // 2. ÖDEME YAPMA EKRANI
    @GetMapping("/yap")
    public String odemeFormu(Model model) {
        model.addAttribute("yeniOdeme", new Odeme());
        model.addAttribute("rezervasyonlar", rezervasyonRepository.findAll());
        return "odeme-yap";
    }

    // 3. KAYDET (AKILLI KONTROL)
    @PostMapping("/kaydet")
    public String kaydet(@ModelAttribute Odeme odeme) {
        
        // Rezervasyonu bul
        Rezervasyon rez = rezervasyonRepository.findById(odeme.getRezervasyon().getRezervasyonId()).orElse(null);

        if (rez != null) {
            // A) Daha önce ne kadar ödenmiş?
            Double dahaOnceOdenen = odemeRepository.toplamOdenenTutar(rez.getRezervasyonId());
            if (dahaOnceOdenen == null) dahaOnceOdenen = 0.0;

            // B) Kalan Borç
            Double toplamBorc = rez.getToplamTutar();
            Double kalanBorc = toplamBorc - dahaOnceOdenen;

            // KONTROL: Fazla ödeme mi?
            // (Küçük kuruş farklarını yok saymak için 0.1 tolerans bırakabilirsin ama şimdilik katı kural koyalım)
            if (odeme.getTutar() > kalanBorc) {
                // Hata ver ve geri gönder (Basitçe sayfayı yeniliyoruz, hata parametresi eklenebilir)
                return "redirect:/odemeler/yap?hata=fazla_odeme&kalan=" + kalanBorc;
            }
        }

        // Tarihi şu an olarak ayarla
        odeme.setOdemeTarihi(LocalDateTime.now());
        
        odemeRepository.save(odeme);
        return "redirect:/odemeler";
    }
}