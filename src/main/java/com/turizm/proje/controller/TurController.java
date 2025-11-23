package com.turizm.proje.controller;

import com.turizm.proje.entity.Tur;
import com.turizm.proje.repository.MusteriRepository;
import com.turizm.proje.repository.RezervasyonRepository;
import com.turizm.proje.repository.TurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TurController {

    @Autowired private TurRepository turRepository;
    @Autowired private MusteriRepository musteriRepository;
    @Autowired private RezervasyonRepository rezervasyonRepository;


    @GetMapping("/")
    public String anaSayfa(Model model) {
        long turSayisi = turRepository.count();
        long musteriSayisi = musteriRepository.count();
        
        Double ciro = rezervasyonRepository.toplamCiroHesapla();
        if (ciro == null) ciro = 0.0;

        model.addAttribute("turSayisi", turSayisi);
        model.addAttribute("musteriSayisi", musteriSayisi);
        model.addAttribute("toplamCiro", ciro);

        return "index";
    }

 
    @GetMapping("/turlar")
    public String turlariListele(Model model, @RequestParam(required = false) String kelime) {
        if (kelime != null && !kelime.isEmpty()) {  
            model.addAttribute("listemiz", turRepository.turAra(kelime));
            model.addAttribute("arananKelime", kelime);
        } else {
            model.addAttribute("listemiz", turRepository.findAll());
        }
        return "turlar";
    }


    @GetMapping("/turlar/ekle")
    public String yeniTurFormu(Model model) {
        model.addAttribute("yeniTur", new Tur());
        return "tur-ekle";
    }


    @PostMapping("/turlar/kaydet")
    public String turKaydet(@ModelAttribute Tur tur) {
        
        if (tur.getTurKodu() == null || tur.getTurKodu().isEmpty()) {
            tur.setTurKodu("TR-" + System.currentTimeMillis());
        }

        if (tur.getResimUrl() == null || tur.getResimUrl().trim().isEmpty()) {
            tur.setResimUrl(null); 
        }
        
        if (tur.getMinKisi() == null) {
            tur.setMinKisi(1);
        }
        
        if (tur.getAktif() == null) {
            tur.setAktif(true);
        }

        turRepository.save(tur);
        return "redirect:/turlar";
    }


    @GetMapping("/turlar/duzenle/{id}")
    public String turDuzenle(@PathVariable Long id, Model model) {
        Tur bulunanTur = turRepository.findById(id).orElse(null);
        model.addAttribute("yeniTur", bulunanTur);
        return "tur-ekle";
    }


    @GetMapping("/turlar/sil/{id}")
    public String turSil(@PathVariable Long id, org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        try {
            turRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("mesaj", "Tur başarıyla silindi.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("hata", "BU TUR SİLİNEMEZ! Çünkü aktif rezervasyonları var.");
        }
        return "redirect:/turlar";
    }
}