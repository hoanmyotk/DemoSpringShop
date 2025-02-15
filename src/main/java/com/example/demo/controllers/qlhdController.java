package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.models.LichSu;
import com.example.demo.models.User;
import com.example.demo.services.ChiTietHoaDonService;
import com.example.demo.services.HoaDonService;
import com.example.demo.services.LichSuService;

import jakarta.servlet.http.HttpSession;
@Controller
@RequestMapping("/qlhd")
public class qlhdController {
	@Autowired
    private LichSuService lichSuservice;
	@Autowired
    private ChiTietHoaDonService cthdservice;
	@Autowired
    private HoaDonService hoaDonservice;

	@RequestMapping
    public String getAllLichSu(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
		User user = (User)session.getAttribute("user");
    	if (user == null) {
    		redirectAttributes.addFlashAttribute("errorUser", "Vui lòng đăng nhập!");
    		return "redirect:/admin/sanpham";
		}
        List<LichSu> lichsuList = lichSuservice.getAllLichSu();
        model.addAttribute("lichsuList", lichsuList);
        return "qlhd"; // Trả về file lichsu.html
    }
	@PostMapping("/xoa/{id}")
	public String deleteLichSu(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
		User user = (User)session.getAttribute("user");
    	if (user == null) {
    		redirectAttributes.addFlashAttribute("errorUser", "Vui lòng đăng nhập!");
    		return "redirect:/admin/sanpham";
		}
	    // Xóa lịch sử giao dịch theo ID
	    cthdservice.deleteById(id);

	    // Chuyển hướng lại trang lịch sử
	    return "redirect:/qlhd";
	}
	@PostMapping("/xacnhan/{maHD}")
	public String xacNhanHoaDon(@PathVariable Long maHD, HttpSession session, RedirectAttributes redirectAttributes) {
		User user = (User)session.getAttribute("user");
    	if (user == null) {
    		redirectAttributes.addFlashAttribute("errorUser", "Vui lòng đăng nhập!");
    		return "redirect:/admin/sanpham";
		}
	    hoaDonservice.updateDaMuaToTrue(maHD);
	    return "redirect:/qlhd";
	}
}
