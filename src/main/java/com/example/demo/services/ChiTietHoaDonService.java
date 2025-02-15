package com.example.demo.services;

import com.example.demo.models.ChiTietHoaDon;
import com.example.demo.repositories.ChiTietHoaDonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChiTietHoaDonService {

    @Autowired
    private ChiTietHoaDonRepository chiTietHoaDonRepository;

    public ChiTietHoaDon createChiTietHoaDon(Long maHD, String maSP, int soLuong) {
        ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon();
        chiTietHoaDon.setMaHD(maHD);
        chiTietHoaDon.setMaSP(maSP);
        chiTietHoaDon.setSoLuong(soLuong);
        return chiTietHoaDonRepository.save(chiTietHoaDon);
    }
    public void deleteById(Long id) {
    	chiTietHoaDonRepository.deleteById(id);
    }
}
