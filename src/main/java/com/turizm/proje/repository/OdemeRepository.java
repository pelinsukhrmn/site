package com.turizm.proje.repository;
import com.turizm.proje.entity.Odeme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OdemeRepository extends JpaRepository<Odeme, Long> {
    @Query("SELECT COALESCE(SUM(o.tutar), 0) FROM Odeme o WHERE o.rezervasyon.rezervasyonId = :rezId")
    Double toplamOdenenTutar(@Param("rezId") Long rezId);
}