package com.turizm.proje.entity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "musteriler")
public class Musteri {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "musteri_id") private Long musteriId;

    @Column(name = "ad") private String ad;
    @Column(name = "soyad") private String soyad;
    @Column(name = "email", unique = true) private String email;
    @Column(name = "telefon_numara") private String telefon;
    @Column(name = "musteri_tipi") private String musteriTipi;
    @Column(name = "tc_kimlik") private String tcKimlik;
    @Column(name = "pasaport_no") private String pasaportNo;
    
    @Column(name = "ulke_id") private Long ulkeId;
}