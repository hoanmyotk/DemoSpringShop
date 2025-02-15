package com.example.demo.services;

import com.example.demo.models.KhachHang;
import com.example.demo.repositories.KhachHangRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KhachHangService {

    @Autowired
    private KhachHangRepository khachHangRepository;
    public KhachHang getKhachHangByTenDNAndPass(String tenDN, String pass) {
        return khachHangRepository.findByTenDNAndMatKhau(tenDN, pass);
    }
    public KhachHang getKhachHangByTenDN(String tenDN) {
        return khachHangRepository.findByTenDN(tenDN);
    }
    public KhachHang findByMaKH(String maKH) {
        return khachHangRepository.findByMaKH(maKH);
    }
    public KhachHang saveKhachHang(KhachHang khachHang) {
        return khachHangRepository.save(khachHang);
    }
}
