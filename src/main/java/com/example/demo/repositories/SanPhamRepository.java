package com.example.demo.repositories;

import com.example.demo.models.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, String> {
	Page<SanPham> findByMaLoai(String maLoai, Pageable pageable);
	SanPham findByMaSP(String maSP);
	Page<SanPham> findByTenSPContainingIgnoreCase(String keyword, Pageable pageable);
}
