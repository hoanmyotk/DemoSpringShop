package com.example.demo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "V_LichSu") // Ánh xạ đến view V_LichSu
public class LichSu {

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "TENKH")
    private String tenKhachHang;

    @Column(name = "TENSP")
    private String tenSanPham;

    @Column(name = "ANH")
    private String anhSanPham;

    @Column(name = "GIA")
    private Long giaSanPham;

    @Column(name = "SOLUONG")
    private int soLuong;

    @Column(name = "THANHTIEN")
    private Long thanhTien;

    @Column(name = "NGAYLAP")
    private LocalDate ngayLap;

	@Column(name = "MAKH")
    private String maKH;
    @Column(name = "MAHD")
    private Long maHD;
    @Column(name = "DAMUA")
    private boolean daMua;

    // Getters và Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public String getMaKH() {
        return maKH;
    }
    public Long getMaHD() {
        return maHD;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getAnhSanPham() {
        return anhSanPham;
    }

    public void setAnhSanPham(String anhSanPham) {
        this.anhSanPham = anhSanPham;
    }

    public Long getGiaSanPham() {
        return giaSanPham;
    }

    public void setGiaSanPham(Long giaSanPham) {
        this.giaSanPham = giaSanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public Long getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(Long thanhTien) {
        this.thanhTien = thanhTien;
    }

    public LocalDate getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(LocalDate ngayLap) {
        this.ngayLap = ngayLap;
    }

    public boolean isDaMua() {
        return daMua;
    }

    public void setDaMua(boolean daMua) {
        this.daMua = daMua;
    }
}
