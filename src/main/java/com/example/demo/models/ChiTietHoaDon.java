package com.example.demo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "CHITIETHOADON")
public class ChiTietHoaDon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "MAHD", nullable = false)
    private Long maHD;

    @Column(name = "MASP", length = 10, nullable = false)
    private String maSP;

    @Column(name = "SOLUONG", nullable = false)
    private int soLuong;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMaHD() {
        return maHD;
    }

    public void setMaHD(Long maHD) {
        this.maHD = maHD;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
