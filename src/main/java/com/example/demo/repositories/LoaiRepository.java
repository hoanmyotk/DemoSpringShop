package com.example.demo.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Loai;
@Repository
public interface LoaiRepository extends JpaRepository<Loai, String> {
	
}
