package com.example.demo.repositories;

import com.example.demo.models.Loai;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class LoaiCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Loai> findAllWithSoLuongSanPham() {
        String sql = """
            SELECT l.maloai, l.tenloai, l.anh, ISNULL(COUNT(sp.MASP), 0) AS soLuongSanPham
        	FROM loai l
        	LEFT JOIN sanpham sp ON l.maloai = sp.maloai
        	GROUP BY l.maloai, l.tenloai, l.anh;
        """;

        List<Object[]> results = entityManager.createNativeQuery(sql).getResultList();

        List<Loai> loaiList = new ArrayList<>();
        for (Object[] row : results) {
            Loai loai = new Loai();
            loai.setMaLoai((String) row[0]);
            loai.setTenLoai((String) row[1]);
            loai.setAnh((String) row[2]);
            loai.setSoLuongSanPham(((Number) row[3]).intValue());
            loaiList.add(loai);
        }

        return loaiList;
    }
}

