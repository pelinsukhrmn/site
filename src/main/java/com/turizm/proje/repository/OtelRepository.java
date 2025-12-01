// Örnek: OtelRepository
package com.turizm.proje.repository;
import com.turizm.proje.entity.Otel;
import org.springframework.data.jpa.repository.JpaRepository;
public interface OtelRepository extends JpaRepository<Otel, Long> {}