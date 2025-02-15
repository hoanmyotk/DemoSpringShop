package com.example.demo.services;

import com.example.demo.models.SanPham;
import com.example.demo.repositories.SanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public class SanPhamService {

    @Autowired
    private SanPhamRepository sanPhamRepository;

    // Lấy tất cả sản phẩm
    public List<SanPham> getAllSanPham() {
        return sanPhamRepository.findAll();
    }

    // Lấy sản phẩm theo mã sản phẩm
    public Optional<SanPham> getSanPhamById(String id) {
        return sanPhamRepository.findById(id);
    }

    // Thêm mới hoặc cập nhật sản phẩm
    public SanPham saveOrUpdateSanPham(SanPham sanPham) {
        return sanPhamRepository.save(sanPham);
    }

    // Xóa sản phẩm theo mã sản phẩm
    public void deleteSanPhamById(String id) {
        sanPhamRepository.deleteById(id);
    }
    public Page<SanPham> getSanPhamPageWithLoai(String maLoai, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        if (maLoai != null && !maLoai.isEmpty()) {
            return sanPhamRepository.findByMaLoai(maLoai, pageable);  // Tìm theo mã loại
        } else {
            return sanPhamRepository.findAll(pageable);  // Lấy tất cả sản phẩm nếu không có mã loại
        }
    }
    public Page<SanPham> searchSanPhamByKeyword(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return sanPhamRepository.findByTenSPContainingIgnoreCase(keyword, pageable);
    }
    public boolean existsByMaSP(String maSP) {
        return sanPhamRepository.existsById(maSP);
    }
    public SanPham findByMaSP(String maSP) {
    	return sanPhamRepository.findByMaSP(maSP);
    }
}
