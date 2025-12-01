package com.turizm.proje.repository;
import com.turizm.proje.entity.Calisan;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CalisanRepository extends JpaRepository<Calisan, Long> {
    Calisan findByEmailAndTcKimlik(String email, String tcKimlik);
}