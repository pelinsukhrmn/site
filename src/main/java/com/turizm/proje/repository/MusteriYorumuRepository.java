package com.turizm.proje.repository;

import com.turizm.proje.entity.MusteriYorumu;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MusteriYorumuRepository extends JpaRepository<MusteriYorumu, Long> {
    
    // İleride lazım olabilecek bazı özel sorgular (Opsiyonel):
    
    // 1. Belirli bir Tura ait yorumları getir
    List<MusteriYorumu> findByTur_TurId(Long turId);
    
    // 2. Sadece Onaylanmış yorumları getir (Admin onayı sistemi için)
    List<MusteriYorumu> findByOnayDurumuTrue();
}