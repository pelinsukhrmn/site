package com.turizm.proje.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "musteri_yorumlari")
public class MusteriYorumu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "yorum_id")
    private Long yorumId;

    // İLİŞKİ 1: Yorumu hangi müşteri yaptı?
    @ManyToOne
    @JoinColumn(name = "musteri_id")
    private Musteri musteri;

    // İLİŞKİ 2: Yorum hangi tura yapıldı?
    @ManyToOne
    @JoinColumn(name = "tur_id")
    private Tur tur;

    @Column(name = "yorum_metni")
    private String yorumMetni;

    @Column(name = "puan")
    private Integer puan; // 1 ile 5 arası

    @Column(name = "onay_durumu")
    private Boolean onayDurumu; // Admin onayladı mı? (True/False)

    @Column(name = "yorum_tarihi")
    private LocalDateTime yorumTarihi;
}