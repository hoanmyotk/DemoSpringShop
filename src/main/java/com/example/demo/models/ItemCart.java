package com.example.demo.models;

public class ItemCart {

    private String maSP; // Mã sản phẩm
    private String tenSP; // Tên sản phẩm
    private long gia; // Giá sản phẩm
    private int soLuong; // Số lượng sản phẩm trong giỏ
    private long thanhTien; // Thành tiền (gia * soLuong)
    private String anh; // Đường dẫn hoặc URL ảnh sản phẩm

    // Constructors
    public ItemCart() {
    }

    public ItemCart(String maSP, String tenSP, long gia, int soLuong, String anh) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.gia = gia;
        this.soLuong = soLuong;
        this.anh = anh;
        this.thanhTien = gia * soLuong;
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
        // Update thanhTien whenever gia is updated
        this.thanhTien = this.gia * this.soLuong;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
        // Update thanhTien whenever soLuong is updated
        this.thanhTien = this.gia * this.soLuong;
    }

    public long getThanhTien() {
        return thanhTien;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    @Override
    public String toString() {
        return "ItemCart{" +
                "maSP='" + maSP + '\'' +
                ", tenSP='" + tenSP + '\'' +
                ", gia=" + gia +
                ", soLuong=" + soLuong +
                ", thanhTien=" + thanhTien +
                ", anh='" + anh + '\'' +
                '}';
    }
}
