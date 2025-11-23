package com.turizm.proje.entity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "musteri_yorumlari")
public class MusteriYorumu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "yorum_id")
    private Long yorumId;

    @ManyToOne
    @JoinColumn(name = "musteri_id")
    private Musteri musteri;

    @ManyToOne
    @JoinColumn(name = "tur_id")
    private Tur tur;

    @Column(name = "yorum_metni")
    private String yorumMetni;

    @Column(name = "puan")
    private Integer puan;

    @Column(name = "onay_durumu")
    private Boolean onayDurumu;
}