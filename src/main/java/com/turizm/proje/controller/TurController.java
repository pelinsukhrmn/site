package com.turizm.proje.controller;
import com.turizm.proje.entity.Tur;
import com.turizm.proje.repository.*;
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
        Double ciro = rezervasyonRepository.toplamCiroHesapla();
        if (ciro == null) ciro = 0.0;
        model.addAttribute("turSayisi", turRepository.count());
        model.addAttribute("musteriSayisi", musteriRepository.count());
        model.addAttribute("toplamCiro", ciro);
        return "index";
    }

    @GetMapping("/turlar")
    public String turlariListele(Model model, @RequestParam(required=false) String kelime, @RequestParam(required=false) Long ulkeId) {
        if (kelime != null && !kelime.isEmpty()) model.addAttribute("listemiz", turRepository.turAra(kelime));
        else if (ulkeId != null) model.addAttribute("listemiz", turRepository.findByUlke_UlkeId(ulkeId));
        else model.addAttribute("listemiz", turRepository.findAll());
        return "turlar";
    }

    @GetMapping("/turlar/ekle")
    public String ekleFormu(Model model) {
        model.addAttribute("yeniTur", new Tur());
        return "tur-ekle";
    }

    @PostMapping("/turlar/kaydet")
    public String kaydet(@ModelAttribute Tur tur) {
        if (tur.getTurKodu() == null || tur.getTurKodu().isEmpty()) tur.setTurKodu("TR-" + System.currentTimeMillis());
        if (tur.getMinKisi() == null) tur.setMinKisi(1);
        turRepository.save(tur);
        return "redirect:/turlar";
    }

    @GetMapping("/turlar/sil/{id}")
    public String sil(@PathVariable Long id) {
        turRepository.deleteById(id);
        return "redirect:/turlar";
    }
}