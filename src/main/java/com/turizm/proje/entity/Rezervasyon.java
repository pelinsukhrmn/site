package com.turizm.proje.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "rezervasyonlar")
public class Rezervasyon {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rezervasyon_id") private Long rezervasyonId;

    @Column(name = "rezervasyon_no") private String rezervasyonNo;

    @ManyToOne @JoinColumn(name = "musteri_id") private Musteri musteri;
    @ManyToOne @JoinColumn(name = "tur_id") private Tur tur;

    @Column(name = "kisi_sayisi") private Integer kisiSayisi;
    @Column(name = "baslangic_tarihi") private LocalDate baslangicTarihi;
    @Column(name = "toplam_tutar") private Double toplamTutar;
    @Column(name = "durum") private String durum;
    @Column(name = "kapora_odendi") private Boolean kaporaOdendi;
}