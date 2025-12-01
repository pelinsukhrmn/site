package com.turizm.proje.controller;
import com.turizm.proje.entity.Rezervasyon;
import com.turizm.proje.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/rezervasyonlar")
public class RezervasyonController {
    @Autowired private RezervasyonRepository rezervasyonRepository;
    @Autowired private MusteriRepository musteriRepository;
    @Autowired private TurRepository turRepository;

    @GetMapping
    public String listele(Model model) {
        model.addAttribute("rezListesi", rezervasyonRepository.findAll());
        return "rezervasyonlar";
    }
    
    @GetMapping("/ekle")
    public String ekleFormu(Model model, 
                            @RequestParam(required = false) String hata,
                            @RequestParam(required = false) Long turId) {
        
        Rezervasyon yeniRez = new Rezervasyon();
        if (turId != null) {
            turRepository.findById(turId).ifPresent(yeniRez::setTur);
        }

        model.addAttribute("yeniRez", yeniRez);
        model.addAttribute("musteriler", musteriRepository.findAll());
        model.addAttribute("turlar", turRepository.findAll());
        
        if (hata != null) {
            model.addAttribute("hataMesaji", "İşlem sırasında bir hata oluştu: " + hata);
        }
        
        return "rezervasyon-ekle";
    }

    @PostMapping("/kaydet")
    public String kaydet(@ModelAttribute Rezervasyon rezervasyon) {
        if (rezervasyon.getRezervasyonNo() == null || rezervasyon.getRezervasyonNo().isEmpty()) 
            rezervasyon.setRezervasyonNo("REZ-" + System.currentTimeMillis());
        
        if (rezervasyon.getToplamTutar() == null || rezervasyon.getToplamTutar() == 0.0) {
            var tur = turRepository.findById(rezervasyon.getTur().getTurId()).orElse(null);
            if (tur != null) rezervasyon.setToplamTutar(tur.getBaslangicFiyati() * rezervasyon.getKisiSayisi());
        }
        
        if (rezervasyon.getDurum() == null) rezervasyon.setDurum("BEKLIYOR");
        rezervasyonRepository.save(rezervasyon);
        return "redirect:/rezervasyonlar";
    }

    @GetMapping("/sil/{id}")
    public String sil(@PathVariable Long id) {
        rezervasyonRepository.deleteById(id);
        return "redirect:/rezervasyonlar";
    }
    @GetMapping("/duzenle/{id}")
    public String duzenleFormu(@PathVariable Long id, Model model) {
        Rezervasyon mevcutRez = rezervasyonRepository.findById(id).orElse(null);
        
        if (mevcutRez != null) {
            model.addAttribute("yeniRez", mevcutRez);
            model.addAttribute("musteriler", musteriRepository.findAll());
            model.addAttribute("turlar", turRepository.findAll());
            return "rezervasyon-ekle";
        }
        
        return "redirect:/rezervasyonlar";
    }
}