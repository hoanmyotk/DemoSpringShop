package com.example.demo.services;

import com.example.demo.models.Loai;
import com.example.demo.repositories.LoaiCustomRepository;
import com.example.demo.repositories.LoaiRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoaiService {

    @Autowired
    private LoaiCustomRepository loaiCustomRepository;
    @Autowired
    private LoaiRepository loaiRepository;
    // Lấy tất cả các loại kèm số lượng sản phẩm
    public List<Loai> getAllLoaiWithSoLuongSanPham() {
        return loaiCustomRepository.findAllWithSoLuongSanPham();
    }
}
