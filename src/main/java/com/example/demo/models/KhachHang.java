package com.example.demo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "KHACHHANG") // Thay "YOUR_TABLE_NAME" bằng tên bảng thực tế, ví dụ: "KHACHHANG".
public class KhachHang {

    @Id
    @Column(name = "MAKH", length = 10, nullable = false)
    private String maKH;

    @Column(name = "TENKH", length = 50, nullable = false)
    private String tenKH;

    @Column(name = "SDT", length = 15)
    private String sdt;

    @Column(name = "DIACHI", length = 100)
    private String diaChi;

    @Column(name = "ANH", length = 100)
    private String anh;

    @Column(name = "EMAIL", length = 50, nullable = false)
    private String email;

    @Column(name = "TENDN", length = 50, nullable = false)
    private String tenDN;

    @Column(name = "MATKHAU", length = 50, nullable = false)
    private String matKhau;

    // Getters and Setters
    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTenDN() {
        return tenDN;
    }

    public void setTenDN(String tenDN) {
        this.tenDN = tenDN;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
}
