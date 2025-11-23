package com.turizm.proje.repository;

import com.turizm.proje.entity.FiyatDonemi;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FiyatDonemiRepository extends JpaRepository<FiyatDonemi, Long> {
    // İleride "Şu tarihler arası indirimleri getir" dersek buraya kod yazarız
}