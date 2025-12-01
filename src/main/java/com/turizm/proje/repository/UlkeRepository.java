package com.turizm.proje.repository;

import com.turizm.proje.entity.Ulke;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UlkeRepository extends JpaRepository<Ulke, Long> {
    // Standart işlemler (Kaydet, Sil, Listele, Bul) otomatik gelir.
    // Ekstra SQL yazmaya gerek yok.
}