package com.example.demo.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.models.Loai;
import com.example.demo.models.SanPham;
import com.example.demo.repositories.LoaiCustomRepository;
import com.example.demo.repositories.LoaiRepository;
import com.example.demo.repositories.SanPhamRepository;
import com.example.demo.services.LoaiService;
import com.example.demo.services.SanPhamService;

import java.util.List;

@Controller
@RequestMapping("/store")
public class SanPhamController {

    @Autowired
    private SanPhamService sanPhamService;
    @Autowired
    private LoaiService loaiService;
    @GetMapping
    public String hienThiSanPham(Model model) {
        // Lấy danh sách sản phẩm từ cơ sở dữ liệu
        List<SanPham> danhSachSanPham = sanPhamService.getAllSanPham();
        List<Loai> danhSachLoai = loaiService.getAllLoaiWithSoLuongSanPham();
        // Đưa danh sách sản phẩm vào Model để hiển thị trên View
        model.addAttribute("danhSachSanPham", danhSachSanPham);
        model.addAttribute("danhSachLoai", danhSachLoai);
        return "store"; // Trả về tên view (sanpham.html)
    }
}

