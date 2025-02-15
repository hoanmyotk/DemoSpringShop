package com.example.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "LOAI")
public class Loai {

    @Id
    @Column(name = "MALOAI", length = 10, nullable = false)
    private String maLoai;

    @Column(name = "TENLOAI", length = 50, nullable = false)
    private String tenLoai;

    @Column(name = "ANH", length = 100)
    private String anh;
    @Transient
    private int soLuongSanPham;
    // Getters and Setters
    public String getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }
    public int getSoLuongSanPham() {
        return soLuongSanPham;
    }

    public void setSoLuongSanPham(int soLuongSanPham) {
        this.soLuongSanPham = soLuongSanPham;
    }
}
