package com.turizm.proje.controller;

import com.turizm.proje.entity.Odeme;
import com.turizm.proje.repository.OdemeRepository;
import com.turizm.proje.repository.RezervasyonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/odemeler")
public class OdemeController {

    @Autowired private OdemeRepository odemeRepository;
    @Autowired private RezervasyonRepository rezervasyonRepository;

    @GetMapping
    public String listele(Model model) {
        model.addAttribute("odemeListesi", odemeRepository.findAll());
        return "odemeler";
    }

    @GetMapping("/yap")
    public String odemeFormu(Model model) {
        model.addAttribute("yeniOdeme", new Odeme());
        model.addAttribute("rezervasyonlar", rezervasyonRepository.findAll());
        return "odeme-yap";
    }
    // ÖDEMEYİ KAYDET (GÜVENLİ VERSİYON)
    @PostMapping("/kaydet")
    public String kaydet(@ModelAttribute Odeme odeme) {
        
        // 1. Rezervasyon Bilgilerini Al
        // (Hangi rezervasyona ödeme yapılıyor?)
        com.turizm.proje.entity.Rezervasyon rez = rezervasyonRepository.findById(odeme.getRezervasyon().getRezervasyonId()).orElse(null);

        if (rez != null) {
            // 2. Şu ana kadar ne kadar ödenmiş?
            Double dahaOnceOdenen = odemeRepository.toplamOdenenTutar(rez.getRezervasyonId());
            
            // 3. Kalan Borç Ne Kadar?
            Double toplamBorc = rez.getToplamTutar();
            Double kalanBorc = toplamBorc - dahaOnceOdenen;

            // KONTROL 1: Zaten borç bitmiş mi?
            if (kalanBorc <= 0) {
                return "redirect:/odemeler/yap?hata=borc_yok";
            }

            // KONTROL 2: Ödenmek istenen miktar, kalan borçtan fazla mı?
            if (odeme.getTutar() > kalanBorc) {
                // Hata ver: "Sadece X TL kaldı, sen Y TL ödemeye çalışıyorsun"
                return "redirect:/odemeler/yap?hata=fazla_odeme&kalan=" + kalanBorc;
            }
        }

        // Her şey uygunsa tarihi at ve kaydet
        odeme.setOdemeTarihi(java.time.LocalDateTime.now());
        odemeRepository.save(odeme);
        
        return "redirect:/odemeler";
    }
}