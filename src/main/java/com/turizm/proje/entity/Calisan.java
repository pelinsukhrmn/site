package com.turizm.proje.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "calisanlar")
public class Calisan {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "calisan_id") private Long calisanId;

    @Column(name = "tc_kimlik") private String tcKimlik;
    @Column(name = "ad") private String ad;
    @Column(name = "soyad") private String soyad;
    @Column(name = "email") private String email;
    @Column(name = "calisan_tipi") private String calisanTipi;
    @Column(name = "maas") private Double maas;
    @Column(name = "ise_baslama_tarihi") private LocalDate iseBaslamaTarihi;
    @Column(name = "telefon") private String telefon;
}