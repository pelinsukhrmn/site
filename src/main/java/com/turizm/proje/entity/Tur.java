package com.turizm.proje.entity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "turlar")
public class Tur {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tur_id") private Long turId;

    @Column(name = "tur_adi") private String turAdi;
    @Column(name = "tur_kodu") private String turKodu;
    @Column(name = "baslangic_fiyati") private Double baslangicFiyati;
    @Column(name = "sure_gun") private Integer sureGun;
    @Column(name = "aciklama") private String aciklama;
    @Column(name = "kontenjan") private Integer kontenjan;
    @Column(name = "resim_url") private String resimUrl;
    @Column(name = "aktif") private Boolean aktif;
    @Column(name = "min_kisi") private Integer minKisi;

    @ManyToOne
    @JoinColumn(name = "ulke_id")
    private Ulke ulke;
}