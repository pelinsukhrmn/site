package com.turizm.proje.controller;

import com.turizm.proje.entity.Musteri;
import com.turizm.proje.repository.MusteriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/musteriler")
public class MusteriController {

    @Autowired
    private MusteriRepository musteriRepository;

    // 1. LİSTELEME
    @GetMapping
    public String listele(Model model) {
        model.addAttribute("musteriListesi", musteriRepository.findAll());
        return "musteriler";
    }

    // 2. EKLEME SAYFASI AÇ
    @GetMapping("/ekle")
    public String ekleFormu(Model model) {
        model.addAttribute("yeniMusteri", new Musteri());
        return "musteri-ekle";
    }

    @PostMapping("/kaydet")
    public String kaydet(@ModelAttribute Musteri musteri, Model model) {
        try {
            // 1. KONTROLLER
            if (musteri.getAd() == null || musteri.getAd().isEmpty()) throw new RuntimeException("Ad zorunludur.");
            if (musteri.getEmail() == null || musteri.getEmail().isEmpty()) throw new RuntimeException("Email zorunludur.");

            // 2. YABANCI / YERLİ AYRIMI
            if ("YABANCI".equals(musteri.getMusteriTipi())) {
                musteri.setTcKimlik(null); // Yabancının TC'si olmaz
                if (musteri.getPasaportNo() == null || musteri.getPasaportNo().isEmpty()) {
                    throw new RuntimeException("Yabancı müşteriler için Pasaport No zorunludur!");
                }
            } else {
                musteri.setMusteriTipi("YERLI");
                musteri.setPasaportNo(null); // Yerlinin Pasaportunu (varsa da) kaydetme
                if (musteri.getTcKimlik() == null || musteri.getTcKimlik().isEmpty()) {
                    throw new RuntimeException("Yerli müşteriler için TC Kimlik zorunludur!");
                }
            }

            // 3. TELEFON
            if (musteri.getTelefon() != null && musteri.getTelefon().trim().length() < 5) {
                musteri.setTelefon(null);
            }

            // 4. KAYDET
            musteriRepository.save(musteri);
            return "redirect:/musteriler";

        } catch (Exception e) {
            System.out.println("HATA: " + e.getMessage());
            
            String mesaj = "Kayıt yapılamadı: " + e.getMessage();
            if (e.getMessage().contains("ConstraintViolation")) {
                mesaj = "Bu E-Posta, TC Kimlik veya Pasaport numarası zaten kayıtlı!";
            }

            // --- DÜZELTİLEN KISIM BURASI ---
            model.addAttribute("hata", mesaj);
            model.addAttribute("yeniMusteri", musteri); // <--- EKSİK OLAN SATIR BUYDU!
            // Kullanıcının girdiği bilgileri geri gönderiyoruz ki form boş gelmesin
            
            return "musteri-ekle"; 
        }
    }

    // 4. SİL
    @GetMapping("/sil/{id}")
    public String sil(@PathVariable Long id) {
        musteriRepository.deleteById(id);
        return "redirect:/musteriler";
    }
}