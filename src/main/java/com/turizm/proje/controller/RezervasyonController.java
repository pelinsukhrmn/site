package com.turizm.proje.controller;

import com.turizm.proje.entity.Rezervasyon;
import com.turizm.proje.entity.Tur;
import com.turizm.proje.repository.MusteriRepository;
import com.turizm.proje.repository.RezervasyonRepository;
import com.turizm.proje.repository.TurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/rezervasyonlar")
public class RezervasyonController {

    @Autowired
    private RezervasyonRepository rezervasyonRepository;

    @Autowired
    private MusteriRepository musteriRepository;

    @Autowired
    private TurRepository turRepository;

    @GetMapping
    public String listele(Model model) {
        model.addAttribute("rezListesi", rezervasyonRepository.findAll());
        return "rezervasyonlar";
    }
    @GetMapping("/ekle")
    public String ekleFormu(Model model, 
                            @RequestParam(required = false) String hata,
                            @RequestParam(required = false) Long turId) { // turId parametresini ekledik
        
        Rezervasyon yeniRez = new Rezervasyon();

        if (turId != null) {
            Tur secilenTur = turRepository.findById(turId).orElse(null);
            yeniRez.setTur(secilenTur);
        }

        model.addAttribute("yeniRez", yeniRez);
        model.addAttribute("musteriler", musteriRepository.findAll());
        model.addAttribute("turlar", turRepository.findAll());
        
        if (hata != null) {
            model.addAttribute("hataMesaji", "Seçilen turda yeterli kontenjan yok!");
        }
        
        return "rezervasyon-ekle";
    }

    @PostMapping("/kaydet")
    public String kaydet(@ModelAttribute Rezervasyon rezervasyon) {
        
        Tur secilenTur = null;
        if (rezervasyon.getTur() != null && rezervasyon.getTur().getTurId() != null) {
            secilenTur = turRepository.findById(rezervasyon.getTur().getTurId()).orElse(null);
        }
        if (secilenTur != null) {
            if (rezervasyon.getKisiSayisi() > secilenTur.getKontenjan()) {
                return "redirect:/rezervasyonlar/ekle?hata=yetersiz_kontenjan";
            }
            secilenTur.setKontenjan(secilenTur.getKontenjan() - rezervasyon.getKisiSayisi());
            turRepository.save(secilenTur);
        }
        if (rezervasyon.getToplamTutar() == null || rezervasyon.getToplamTutar() == 0.0) {
            if (secilenTur != null) {
                double hesaplananTutar = secilenTur.getBaslangicFiyati() * rezervasyon.getKisiSayisi();
                rezervasyon.setToplamTutar(hesaplananTutar);
            }
        }
        if (rezervasyon.getRezervasyonNo() == null || rezervasyon.getRezervasyonNo().isEmpty()) {
            rezervasyon.setRezervasyonNo("REZ-" + System.currentTimeMillis());
        }
        if (rezervasyon.getDurum() == null) rezervasyon.setDurum("BEKLIYOR");
        if (rezervasyon.getKaporaOdendi() == null) rezervasyon.setKaporaOdendi(false);

        rezervasyonRepository.save(rezervasyon);
        return "redirect:/rezervasyonlar";
    }


    @GetMapping("/sil/{id}")
    public String sil(@PathVariable Long id, org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        try {
            rezervasyonRepository.deleteById(id);
            
            redirectAttributes.addFlashAttribute("mesaj", "Rezervasyon başarıyla silindi.");
        } catch (Exception e) {

            redirectAttributes.addFlashAttribute("hata", "SİLİNEMEDİ! Bu rezervasyona ait ödeme kayıtları var. Önce ödemeleri silmelisiniz.");
        }
        return "redirect:/rezervasyonlar";
    }
}