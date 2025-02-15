package com.example.demo.services;

import com.example.demo.models.HoaDon;
import com.example.demo.repositories.HoaDonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class HoaDonService {

    @Autowired
    private HoaDonRepository hoaDonRepository;

    public HoaDon createHoaDon(String maKH, boolean daMua) {
        HoaDon hoaDon = new HoaDon();
        hoaDon.setNgayLap(LocalDate.now());
        hoaDon.setMaKH(maKH);
        hoaDon.setDaMua(daMua);
        return hoaDonRepository.save(hoaDon);
    }

    public List<HoaDon> getAllHoaDon() {
        return hoaDonRepository.findAll();
    }
    public HoaDon updateDaMuaToTrue(Long maHD) {
        HoaDon hoaDon = hoaDonRepository.findById(maHD).orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại với ID: " + maHD));
        if (!hoaDon.isDaMua()) {
            hoaDon.setDaMua(true);
            return hoaDonRepository.save(hoaDon);
        }
        return hoaDon; // Trả về nếu đã là true
    }
}
