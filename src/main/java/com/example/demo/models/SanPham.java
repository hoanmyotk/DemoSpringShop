package com.example.demo.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "SANPHAM")
public class SanPham {

    @Id
    @Column(name = "MASP", length = 10, nullable = false)
    private String maSP; // Mã sản phẩm (Primary Key)

    @Column(name = "TENSP", length = 50, nullable = false)
    private String tenSP; // Tên sản phẩm

    @Column(name = "GIA", nullable = false)
    private long gia; // Giá sản phẩm

    @Column(name = "SOLUONG", nullable = false)
    private int soLuong; // Số lượng sản phẩm

    @Column(name = "ANH", length = 100)
    private String anh; // Đường dẫn ảnh (có thể null)

    @Temporal(TemporalType.DATE)
    @Column(name = "NGAYNHAP", nullable = true)
    private Date ngayNhap; // Ngày nhập (có thể null)

    @Column(name = "MALOAI", length = 10, nullable = false)
    private String maLoai; // Mã loại sản phẩm

    // Constructors
    public SanPham() {
    }

    public SanPham(String maSP, String tenSP, long gia, int soLuong, String anh, Date ngayNhap, String maLoai) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.gia = gia;
        this.soLuong = soLuong;
        this.anh = anh;
        this.ngayNhap = ngayNhap;
        this.maLoai = maLoai;
    }

    // Getters and Setters
    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public long getGia() {
        return gia;
    }

    public void setGia(long gia) {
        this.gia = gia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public Date getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(Date ngayNhap) {
        this.ngayNhap = ngayNhap;
    }


    public String getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
    }

    @Override
    public String toString() {
        return "SanPham{" +
                "maSP='" + maSP + '\'' +
                ", tenSP='" + tenSP + '\'' +
                ", gia=" + gia +
                ", soLuong=" + soLuong +
                ", anh='" + anh + '\'' +
                ", ngayNhap=" + ngayNhap +
                ", maLoai='" + maLoai + '\'' +
                '}';
    }
}

