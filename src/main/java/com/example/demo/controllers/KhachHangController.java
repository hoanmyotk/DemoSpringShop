package com.example.demo.controllers;

import com.example.demo.models.KhachHang;
import com.example.demo.services.ItemCartService;
import com.example.demo.services.KhachHangService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/form-sign-in-up")
public class KhachHangController {

    @Autowired
    private KhachHangService khachHangService;
    private static final String RECAPTCHA_SECRET_KEY = "6LcSULMqAAAAAP9f2nzkUHB4Ij2jsyHEAzmCXK6r";
    @GetMapping
    public String showLoginPage() {
        return "dangnhap"; 
    }
    private boolean verifyRecaptcha(String recaptchaResponse) {
        String url = "https://www.google.com/recaptcha/api/siteverify";
        
        // Thực hiện gửi yêu cầu HTTP POST đến Google reCAPTCHA API để xác thực
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        
        String body = "secret=" + RECAPTCHA_SECRET_KEY + "&response=" + recaptchaResponse;
        
        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        
        // Gửi yêu cầu POST đến Google API
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        
        // Kiểm tra phản hồi của Google
        return response.getBody().contains("\"success\": true");
    }
    @PostMapping("/login")
    public String login(
    		@RequestParam("g-recaptcha-response") String recaptchaResponse,
            @RequestParam String username,
            @RequestParam String password,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) {
    	if (!verifyRecaptcha(recaptchaResponse)) {
    		redirectAttributes.addFlashAttribute("errorMessageCaptcha", "Vui lòng xác thực reCAPTCHA.");
            return "redirect:/form-sign-in-up"; 
        }
        // Kiểm tra thông tin đăng nhập
        KhachHang khachHang = khachHangService.getKhachHangByTenDNAndPass(username, password);

        if (khachHang != null) {
            // Đăng nhập thành công, gắn khách hàng vào session
            HttpSession session = request.getSession();
            session.setAttribute("khachHang", khachHang);

            // Chuyển hướng sang trang /sanpham
            return "redirect:/store";
        } else {
            // Đăng nhập thất bại, thêm thông báo lỗi vào model
        	redirectAttributes.addFlashAttribute("errorMessage", "Tên đăng nhập hoặc mật khẩu không đúng!");
            return "redirect:/form-sign-in-up"; 
        }
    }
    @PostMapping("/register")
    public String register(
    		@RequestParam("g-recaptcha-response") String recaptchaResponse,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("password_check") String passwordCheck,
            @RequestParam("adress") String address,
            @RequestParam("fullname") String fullName,
            @RequestParam("phone") String phone,
            @RequestParam("email") String email,
            Model model,
            RedirectAttributes redirectAttributes) {
    	
    	if (!verifyRecaptcha(recaptchaResponse)) {
    		redirectAttributes.addFlashAttribute("errorMessageCaptcha2", "Vui lòng xác thực reCAPTCHA.");
            return "redirect:/form-sign-in-up"; 
        }
        // Kiểm tra mật khẩu khớp
    	if (!password.equals(passwordCheck)) {
    	    redirectAttributes.addFlashAttribute("errorMessage2", "Passwords do not match!");
    	    return "redirect:/form-sign-in-up";
    	}
    	if (khachHangService.getKhachHangByTenDN(username) != null) {
    	    redirectAttributes.addFlashAttribute("errorMessage3", "Tên đăng nhập đã tồn tại!");
    	    return "redirect:/form-sign-in-up";
    	}
        String maKH;
        do {
            maKH = "KH" + (int) (Math.random() * 999);
        } while (khachHangService.findByMaKH(maKH)!=null);
        // Tạo đối tượng KhachHang
        KhachHang khachHang = new KhachHang();
        khachHang.setMaKH(maKH);
        khachHang.setTenDN(username);
        khachHang.setMatKhau(password);
        khachHang.setDiaChi(address);
        khachHang.setTenKH(fullName);
        khachHang.setAnh("/images/user/avt.jpg");
        khachHang.setSdt(phone);
        khachHang.setEmail(email);

        // Gọi service để lưu khách hàng vào DB
        khachHangService.saveKhachHang(khachHang);

        // Thêm thông báo thành công vào RedirectAttributes
        redirectAttributes.addFlashAttribute("successMessage", "Đăng ký thành công!");

        // Redirect đến trang đăng nhập sau khi đăng ký thành công
        return "redirect:/form-sign-in-up";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
    	request.getSession().invalidate();
        return "redirect:/store"; 
    }
    @GetMapping("/profile")
    public String profile(Model model, HttpSession session) {
    	KhachHang khachHang = (KhachHang)session.getAttribute("khachHang");
    	if (khachHang == null) {
    		return "redirect:/form-sign-in-up";
		}
    	model.addAttribute("khachHang", khachHang);
        return "guestProfile"; 
    }
    @PostMapping("/profile/update")
    public String updateProfile(
            @RequestParam("maKH") String maKH,
            @RequestParam("tenKH") String tenKH,
            @RequestParam("sdt") String sdt,
            @RequestParam("diaChi") String diaChi,
            @RequestParam("email") String email,
            @RequestParam("tenDN") String tenDN,
            @RequestParam("matKhau") String matKhau,
            @RequestParam("anh") MultipartFile anh,
            @RequestParam("anhCu") String anhCu,
            HttpSession session) throws IOException {

        // Lấy thông tin khách hàng từ cơ sở dữ liệu
        KhachHang khachHang = khachHangService.findByMaKH(maKH);
        if (khachHang == null) {
            return "redirect:/profile"; 
        }

        // Cập nhật thông tin khách hàng
        khachHang.setTenKH(tenKH);
        khachHang.setSdt(sdt);
        khachHang.setDiaChi(diaChi);
        khachHang.setEmail(email);
        khachHang.setTenDN(tenDN);
        khachHang.setMatKhau(matKhau);

        // Nếu có ảnh mới, xử lý upload ảnh
        if (!anh.isEmpty()) {
            // Lấy tên file
            String fileName = anh.getOriginalFilename();

            // Đặt thư mục lưu ảnh
            String uploadDir = "static/images/avatars/";

            // Đảm bảo thư mục tồn tại
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Lưu ảnh vào thư mục
            Files.copy(anh.getInputStream(), uploadPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

            // Cập nhật đường dẫn ảnh vào đối tượng khách hàng
            khachHang.setAnh("/images/avatars/" + fileName);
        } else {
            // Nếu không có ảnh mới, giữ lại ảnh cũ
            khachHang.setAnh(anhCu);
        }

        // Lưu khách hàng vào cơ sở dữ liệu
        khachHangService.saveKhachHang(khachHang);

        // Cập nhật session
        session.setAttribute("khachHang", khachHang);

        // Quay lại trang profile
        return "redirect:/form-sign-in-up/profile";
    }

}
