package com.turizm.proje.controller;

import com.turizm.proje.entity.Calisan;
import com.turizm.proje.repository.CalisanRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private CalisanRepository calisanRepository;

    @GetMapping("/login")
    public String loginSayfasi() {
        return "login";
    }

    @PostMapping("/login")
    public String girisYap(@RequestParam String email, 
                           @RequestParam String sifre, 
                           HttpSession session, 
                           Model model) {
        
        Calisan calisan = calisanRepository.findByEmailAndTcKimlik(email, sifre);

        if (calisan != null) {
            session.setAttribute("kullanici", calisan);
            
            return "redirect:/";
        } else {
            model.addAttribute("hata", "E-posta veya TC Kimlik hatalı!");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String cikisYap(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}