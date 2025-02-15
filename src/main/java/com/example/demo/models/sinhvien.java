package com.example.demo.models;

public class sinhvien {
private String masv;
private String hoten;
public sinhvien() {
super();
// TODO Auto-generated constructor stub
}
public sinhvien(String masv, String hoten) {
super();
this.masv = masv;
this.hoten = hoten;
}
public String getMasv() {
return masv;
}
public void setMasv(String masv) {
this.masv = masv;
}
public String getHoten() {
return hoten;
}
public void setHoten(String hoten) {
this.hoten = hoten;
}
@Override
public String toString() {
return "sinhvien [masv=" + masv + ", hoten=" + hoten + "]";
}
}

