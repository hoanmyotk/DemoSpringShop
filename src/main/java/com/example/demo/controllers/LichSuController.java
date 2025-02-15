package com.example.demo.controllers;

import com.example.demo.models.KhachHang;
import com.example.demo.models.LichSu;
import com.example.demo.services.ChiTietHoaDonService;
import com.example.demo.services.LichSuService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/lichsu")
public class LichSuController {

	@Autowired
    private LichSuService lichSuservice;
	@Autowired
    private ChiTietHoaDonService cthdservice;

	@RequestMapping
    public String getAllLichSu(Model model, HttpSession session) {
    	KhachHang khachhang = (KhachHang)session.getAttribute("khachHang");
        if (khachhang == null) {
        	return "redirect:/form-sign-in-up";
        }
        // Lấy danh sách lịch sử giao dịch từ service
        List<LichSu> lichsuList = lichSuservice.findByMaKH(khachhang.getMaKH());
        model.addAttribute("lichsuList", lichsuList);
        return "lichsu"; // Trả về file lichsu.html
    }
	@PostMapping("/xoa/{id}")
	public String deleteLichSu(@PathVariable Long id, HttpSession session) {

	    // Xóa lịch sử giao dịch theo ID
	    cthdservice.deleteById(id);

	    // Chuyển hướng lại trang lịch sử
	    return "redirect:/lichsu";
	}
}
