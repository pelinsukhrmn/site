package com.turizm.proje.entity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "oteller")
public class Otel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "otel_id") private Long otelId;
    
    @Column(name = "web_sitesi")
    private String webSitesi;
    @Column(name = "otel_adi") private String otelAdi;
    @Column(name = "yildiz") private Integer yildiz;
    @Column(name = "oda_tipi") private String odaTipi;
    @Column(name = "gece_fiyati") private Double geceFiyati;
    @Column(name = "resim_url") private String resimUrl;
}