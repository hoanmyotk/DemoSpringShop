package com.example.demo.repositories;

import com.example.demo.models.LichSu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface LichSuRepository extends JpaRepository<LichSu, Long> {
    List<LichSu> findByMaKH(String maKH);
}
