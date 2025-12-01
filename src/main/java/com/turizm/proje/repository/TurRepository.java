package com.turizm.proje.repository;
import com.turizm.proje.entity.Tur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface TurRepository extends JpaRepository<Tur, Long> {
    @Query(value = "SELECT * FROM turlar WHERE tur_adi ILIKE CONCAT('%', :kelime, '%')", nativeQuery = true)
    List<Tur> turAra(@Param("kelime") String kelime);
    
    List<Tur> findByUlke_UlkeId(Long ulkeId);
}