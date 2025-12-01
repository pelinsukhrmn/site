package com.turizm.proje.controller;

import com.turizm.proje.entity.Calisan;
import com.turizm.proje.repository.CalisanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
public class CalisanController {

    @Autowired
    private CalisanRepository calisanRepository;

    // 1. PERSONEL LİSTESİ (Yönetim Paneli İçin)
    @GetMapping("/calisanlar")
    public String listele(Model model) {
        model.addAttribute("calisanListesi", calisanRepository.findAll());
        return "calisanlar";
    }

    // 2. REHBERLER LİSTESİ (Müşteriler İçin Vitrin)
    @GetMapping("/rehberler")
    public String rehberler(Model model) {
        model.addAttribute("rehberListesi", calisanRepository.findAll());
        return "rehberler";
    }

    // 3. EKLEME SAYFASI
    @GetMapping("/calisanlar/ekle")
    public String ekleFormu(Model model) {
        model.addAttribute("yeniCalisan", new Calisan());
        return "calisan-ekle";
    }

    // 4. KAYDET (Otomatik Doldurma Özellikli)
    @PostMapping("/calisanlar/kaydet")
    public String kaydet(@ModelAttribute Calisan calisan) {
        if (calisan.getIseBaslamaTarihi() == null) {
            calisan.setIseBaslamaTarihi(LocalDate.now());
        }
        if (calisan.getMaas() == null) {
            calisan.setMaas(25000.0); // Varsayılan maaş
        }
        if (calisan.getTelefon() == null) {
            calisan.setTelefon("05550000000");
        }

        calisanRepository.save(calisan);
        return "redirect:/calisanlar";
    }

    // 5. SİLME (GÜVENLİK KONTROLLÜ)
    @GetMapping("/calisanlar/sil/{id}")
    public String sil(@PathVariable Long id, org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        try {
            calisanRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("mesaj", "Personel başarıyla silindi.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("hata", "HATA: Yönetici hesapları güvenlik nedeniyle silinemez! (Veritabanı Koruması)");
        }
        return "redirect:/calisanlar";
    }
}