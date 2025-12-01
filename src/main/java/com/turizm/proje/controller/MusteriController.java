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
    @Autowired private MusteriRepository musteriRepository;

    @GetMapping
    public String listele(Model model) {
        model.addAttribute("musteriListesi", musteriRepository.findAll());
        return "musteriler";
    }

    @GetMapping("/ekle")
    public String ekleFormu(Model model) {
        model.addAttribute("yeniMusteri", new Musteri());
        return "musteri-ekle";
    }

    @PostMapping("/kaydet")
    public String kaydet(@ModelAttribute Musteri musteri, Model model) {
        try {
            if ("YABANCI".equals(musteri.getMusteriTipi())) { musteri.setTcKimlik(null); } 
            else { musteri.setMusteriTipi("YERLI"); musteri.setPasaportNo(null); }
            
            if (musteri.getTelefon() != null && musteri.getTelefon().trim().length() < 5) musteri.setTelefon(null);
            
            musteriRepository.save(musteri);
            return "redirect:/musteriler";
        } catch (Exception e) {
            model.addAttribute("hata", "Kayıt Başarısız! (TC veya Email çakışması olabilir)");
            model.addAttribute("yeniMusteri", musteri);
            return "musteri-ekle";
        }
    }

    @GetMapping("/sil/{id}")
    public String sil(@PathVariable Long id) {
        musteriRepository.deleteById(id);
        return "redirect:/musteriler";
    }
}