package com.example.demo.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "HOADON")
public class HoaDon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MAHD")
    private Long maHD;

    @Column(name = "NGAYLAP", nullable = false)
    private LocalDate ngayLap;

    @Column(name = "MAKH", length = 10, nullable = false)
    private String maKH;

    @Column(name = "DAMUA", nullable = false)
    private boolean daMua;

    // Getters and Setters
    public Long getMaHD() {
        return maHD;
    }

    public void setMaHD(Long maHD) {
        this.maHD = maHD;
    }

    public LocalDate getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(LocalDate ngayLap) {
        this.ngayLap = ngayLap;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public boolean isDaMua() {
        return daMua;
    }

    public void setDaMua(boolean daMua) {
        this.daMua = daMua;
    }
}
