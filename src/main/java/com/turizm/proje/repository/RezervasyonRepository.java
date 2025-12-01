package com.turizm.proje.repository;
import com.turizm.proje.entity.Rezervasyon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RezervasyonRepository extends JpaRepository<Rezervasyon, Long> {
    @Query("SELECT SUM(r.toplamTutar) FROM Rezervasyon r")
    Double toplamCiroHesapla();
}