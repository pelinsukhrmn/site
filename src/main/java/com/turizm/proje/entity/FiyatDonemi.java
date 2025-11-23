package com.turizm.proje.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "fiyat_donemleri")
public class FiyatDonemi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fiyat_id")
    private Long fiyatId;

    @ManyToOne
    @JoinColumn(name = "tur_id")
    private Tur tur;

    @Column(name = "baslangic_tarihi")
    private LocalDate baslangicTarihi;

    @Column(name = "bitis_tarihi")
    private LocalDate bitisTarihi;

    @Column(name = "yetiskin_fiyati")
    private Double yetiskinFiyati;

    @Column(name = "cocuk_fiyati")
    private Double cocukFiyati;

    @Column(name = "indirim_orani")
    private Integer indirimOrani; 
}