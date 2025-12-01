package com.turizm.proje.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "odemeler")
public class Odeme {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "odeme_id") private Long odemeId;

    @ManyToOne @JoinColumn(name = "rezervasyon_id") private Rezervasyon rezervasyon;

    @Column(name = "tutar") private Double tutar;
    @Column(name = "odeme_tipi") private String odemeTipi;
    @Column(name = "odeme_tarihi") private LocalDateTime odemeTarihi;
}