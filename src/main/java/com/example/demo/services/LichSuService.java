package com.example.demo.services;

import com.example.demo.models.LichSu;
import com.example.demo.repositories.LichSuRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LichSuService {

    @Autowired
    private LichSuRepository repository;

    public List<LichSu> getAllLichSu() {
        return repository.findAll();
    }

    public LichSu getLichSuById(Long id) {
        return repository.findById(id).orElse(null);
    }
    public List<LichSu> findByMaKH(String maKH){
    	return repository.findByMaKH(maKH);
    }
}
