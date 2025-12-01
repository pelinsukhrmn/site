package com.turizm.proje.controller;
import com.turizm.proje.repository.CalisanRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {
    @Autowired private CalisanRepository calisanRepository;

    @GetMapping("/login")
    public String login() { return "login"; }

    @PostMapping("/login")
    public String giris(@RequestParam String email, @RequestParam String sifre, HttpSession session, Model model) {
        var calisan = calisanRepository.findByEmailAndTcKimlik(email, sifre);
        if (calisan != null) { session.setAttribute("kullanici", calisan); return "redirect:/"; }
        model.addAttribute("hata", "Hatalı Giriş!");
        return "login";
    }

    @GetMapping("/logout")
    public String cikis(HttpSession session) { session.invalidate(); return "redirect:/login"; }
}