package com.turizm.proje.controller;

import com.turizm.proje.entity.Calisan;
import com.turizm.proje.repository.CalisanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/calisanlar")
public class CalisanController {

    @Autowired
    private CalisanRepository calisanRepository;

    @GetMapping
    public String listele(Model model) {
        model.addAttribute("calisanListesi", calisanRepository.findAll());
        return "calisanlar";
    }

    @GetMapping("/ekle")
    public String ekleFormu(Model model) {
        model.addAttribute("yeniCalisan", new Calisan());
        return "calisan-ekle";
    }

    @PostMapping("/kaydet")
    public String kaydet(@ModelAttribute Calisan calisan) {
        
        if (calisan.getIseBaslamaTarihi() == null) {
            calisan.setIseBaslamaTarihi(java.time.LocalDate.now());
        }

        if (calisan.getMaas() == null) {
            calisan.setMaas(25000.0);
        }
        if (calisan.getTelefon() == null || calisan.getTelefon().isEmpty()) {
            calisan.setTelefon("05000000000");
        }

        calisanRepository.save(calisan);
        return "redirect:/calisanlar";
    }

    @GetMapping("/sil/{id}")
    public String sil(@PathVariable Long id) {
        calisanRepository.deleteById(id);
        return "redirect:/calisanlar";
    }
}