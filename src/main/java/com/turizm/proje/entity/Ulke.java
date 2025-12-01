package com.turizm.proje.entity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ulkeler")
public class Ulke {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ulke_id") private Long ulkeId;
    @Column(name = "ulke_adi") private String ulkeAdi;
    @Column(name = "ulke_kodu") private String ulkeKodu;
}