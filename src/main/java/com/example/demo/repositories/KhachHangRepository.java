package com.example.demo.repositories;

import com.example.demo.models.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, String> {

    // Tìm khách hàng theo tên đăng nhập và mật khẩu
    KhachHang findByTenDNAndMatKhau(String tenDN, String matKhau);

    // Tìm khách hàng theo tên đăng nhập
    KhachHang findByTenDN(String tenDN);

    // Tìm khách hàng theo email
    Optional<KhachHang> findByEmail(String email);

    // Tìm khách hàng theo mã khách hàng
    KhachHang findByMaKH(String maKH);
}
