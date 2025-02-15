package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.models.KhachHang;
import com.example.demo.models.Loai;
import com.example.demo.models.SanPham;
import com.example.demo.models.User;
import com.example.demo.services.LoaiService;
import com.example.demo.services.SanPhamService;
import com.example.demo.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;  
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Random;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private LoaiService loaiService;
    
    @Autowired
    private UserService userService;
    
    private static final String RECAPTCHA_SECRET_KEY = "6LcSULMqAAAAAP9f2nzkUHB4Ij2jsyHEAzmCXK6r";
    @GetMapping("/dashboard")
    public String ViewDasHBoard(Model model) {
        return "dashboard";  // View để hiển thị sản phẩm theo phân trang
    }

    // Phương thức để lấy sản phẩm theo mã loại và phân trang
    @GetMapping("/sanpham")
    public String getSanPhamPage(Model model,
                                  @RequestParam(value = "page", defaultValue = "1") int page,
                                  @RequestParam(value = "maLoai", required = false) String maLoai,
                                  @RequestParam(value = "search", required = false) String search) {
        
        int size = 5;  // Số lượng sản phẩm trên mỗi trang

        Page<SanPham> sanPhamPage;
        if (search != null && !search.isEmpty()) {
            // Tìm kiếm sản phẩm theo từ khóa
            sanPhamPage = sanPhamService.searchSanPhamByKeyword(search, page - 1, size);
        } else {
            // Lấy sản phẩm theo mã loại (hoặc tất cả nếu mã loại null)
            sanPhamPage = sanPhamService.getSanPhamPageWithLoai(maLoai, page - 1, size);
        }

        // Lấy danh sách loại sản phẩm để hiển thị lên UI
        List<Loai> danhSachLoai = loaiService.getAllLoaiWithSoLuongSanPham();

        // Đưa các thuộc tính vào model để hiển thị trên View
        model.addAttribute("danhSachLoai", danhSachLoai);
        model.addAttribute("sanPhamPage", sanPhamPage);
        model.addAttribute("currentLoai", maLoai);  // Lưu mã loại để giữ lại khi chuyển trang
        model.addAttribute("search", search);      // Lưu từ khóa tìm kiếm để giữ lại khi chuyển trang
        return "qlsanpham";  // View để hiển thị sản phẩm theo phân trang
    }
    @GetMapping("/form-sanpham")
    public String hienThiFormSanPham(
    		HttpSession session,
    		RedirectAttributes redirectAttributes,
            @RequestParam(value = "id", required = false) String id,
            Model model) {
    	User user = (User)session.getAttribute("user");
    	if (user == null) {
    		redirectAttributes.addFlashAttribute("errorUser", "Vui lòng đăng nhập!");
    		return "redirect:/admin/sanpham";
		}
        // Nếu có ID, lấy sản phẩm theo ID, ngược lại thêm sản phẩm mới
        SanPham sanPham = id != null ? sanPhamService.getSanPhamById(id).orElse(null) : null;
        List<Loai> danhSachLoai = loaiService.getAllLoaiWithSoLuongSanPham();

        model.addAttribute("sanPham", sanPham);
        model.addAttribute("danhSachLoai", danhSachLoai);

        return "form-sanpham";
    }
    // Xử lý thêm sản phẩm
    @PostMapping("/create-sanpham")
    public String themSanPham(
    		HttpSession session,
    		RedirectAttributes redirectAttributes,
            @RequestParam("tenSP") String tenSP,
            @RequestParam("gia") long gia,
            @RequestParam("soLuong") int soLuong,
            @RequestParam("maLoai") String maLoai,
            @RequestParam("anh") MultipartFile anh) throws IOException {
    	User user = (User)session.getAttribute("user");
    	if (user == null) {
    		redirectAttributes.addFlashAttribute("errorUser", "Vui lòng đăng nhập!");
    		return "redirect:/admin/sanpham";
		}
        // Tạo mã sản phẩm ngẫu nhiên và kiểm tra trùng lặp
        String maSP;
        do {
            Random random = new Random();
            int randomNumber = random.nextInt(999) + 1; // Số từ 1 đến 999
            maSP = "SP" + String.format("%03d", randomNumber); // Định dạng mã sản phẩm
        } while (sanPhamService.existsByMaSP(maSP)); // Kiểm tra mã sản phẩm đã tồn tại

        // Tạo đối tượng SanPham mới từ các tham số
        SanPham sanPham = new SanPham();
        sanPham.setMaSP(maSP);
        sanPham.setTenSP(tenSP);
        sanPham.setGia(gia);
        sanPham.setSoLuong(soLuong);
        sanPham.setMaLoai(maLoai);

        // Đặt ngày nhập là ngày hiện tại
        sanPham.setNgayNhap(new Date()); // Sử dụng java.util.Date cho ngày hiện tại

        // Xử lý ảnh nếu có
        if (!anh.isEmpty()) {
            String fileName = anh.getOriginalFilename();
            String uploadDir = "static/images/product-page/upload/";

            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Lưu file ảnh vào thư mục "uploads"
            Files.copy(anh.getInputStream(), uploadPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

            // Gán đường dẫn ảnh vào đối tượng sanPham
            sanPham.setAnh("/images/product-page/upload/" + fileName);
        }else {
            // Nếu không có ảnh mới, giữ lại ảnh cũ
            sanPham.setAnh("/images/user/avt.jpg");
        }

        // Lưu sản phẩm vào cơ sở dữ liệu
        sanPhamService.saveOrUpdateSanPham(sanPham);
        redirectAttributes.addFlashAttribute("successUpdateOrCreate", "Thêm sản phẩm thành công!");
        return "redirect:/admin/sanpham"; // Redirect sau khi thành công
    }


    // Xử lý cập nhật sản phẩm
    @PostMapping("/update-sanpham")
    public String capNhatSanPham(
    		RedirectAttributes redirectAttributes,
            @RequestParam("maSP") String maSP, 
            @RequestParam("tenSP") String tenSP, 
            @RequestParam("gia") long gia, 
            @RequestParam("soLuong") int soLuong, 
            @RequestParam("maLoai") String maLoai, 
            @RequestParam("anh") MultipartFile anh,
            @RequestParam("anhCu") String anhCu) throws IOException {

        // Tìm sản phẩm cần cập nhật
        SanPham sanPham = sanPhamService.findByMaSP(maSP);
        if (sanPham == null) {
            return "redirect:/admin/sanpham"; // Nếu không tìm thấy sản phẩm, chuyển hướng về trang danh sách
        }

        // Cập nhật thông tin sản phẩm
        sanPham.setTenSP(tenSP);
        sanPham.setGia(gia);
        sanPham.setSoLuong(soLuong);
        sanPham.setMaLoai(maLoai);

        // Nếu có ảnh mới, xử lý upload ảnh
        if (!anh.isEmpty()) {
            // Lấy tên file ảnh mới
            String fileName = anh.getOriginalFilename();

            // Đặt thư mục lưu ảnh
            String uploadDir = "static/images/product-page/upload/";

            // Đảm bảo thư mục tồn tại trong project
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Lưu ảnh vào thư mục "static/images/product-page/upload"
            Files.copy(anh.getInputStream(), uploadPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

            // Cập nhật đường dẫn ảnh vào đối tượng sanPham
            sanPham.setAnh("/images/product-page/upload/" + fileName);
        } else {
            // Nếu không có ảnh mới, giữ lại ảnh cũ
            sanPham.setAnh(anhCu);
        }

        // Cập nhật sản phẩm vào cơ sở dữ liệu
        sanPhamService.saveOrUpdateSanPham(sanPham);
        redirectAttributes.addFlashAttribute("successUpdateOrCreate", "Cập nhật sản phẩm thành công!");
        // Chuyển hướng đến danh sách sản phẩm
        return "redirect:/admin/sanpham";
    }


    // Xử lý xóa sản phẩm
    @GetMapping("/delete-sanpham")
    public String xoaSanPham(HttpSession session,
    		RedirectAttributes redirectAttributes,
    		@RequestParam("id") String id) {
    	User user = (User)session.getAttribute("user");
    	if (user == null) {
    		redirectAttributes.addFlashAttribute("errorUser", "Vui lòng đăng nhập!");
    		return "redirect:/admin/sanpham";
		}
        sanPhamService.deleteSanPhamById(id);
        return "redirect:/admin/sanpham";
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
            return "redirect:/admin/sanpham"; 
        }
        // Kiểm tra thông tin đăng nhập
        User user = userService.findByUsernameAndPassword(username, password);

        if (user != null) {
            // Đăng nhập thành công, gắn khách hàng vào session
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            // Chuyển hướng sang trang /sanpham
            return "redirect:/admin/sanpham";
        } else {
            // Đăng nhập thất bại, thêm thông báo lỗi vào model
        	redirectAttributes.addFlashAttribute("errorLoginMessage", "Tên đăng nhập hoặc mật khẩu không đúng!");
            return "redirect:/admin/sanpham"; 
        }
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
//        Xóa session với tên 'user'
//        HttpSession session = request.getSession();
//        session.removeAttribute("user");
    	request.getSession().invalidate();
        // Chuyển hướng người dùng về trang sản phẩm
        return "redirect:/admin/sanpham"; 
    }
    
    @PostMapping("/change-password")
    public String changePassword(
            @RequestParam String currentPassword,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (!currentPassword.equals(user.getPassword())) {
            redirectAttributes.addFlashAttribute("errorChangePass", "Mật khẩu hiện tại không đúng!");
            return "redirect:/admin/sanpham";  
        }
        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("errorChangePass", "Mật khẩu mới và xác nhận mật khẩu không khớp!");
            return "redirect:/admin/sanpham";  
        }
        user.setPassword(newPassword);
        userService.saveOrUpdateUser(user);
        redirectAttributes.addFlashAttribute("successChangePass", "Mật khẩu của bạn đã được thay đổi thành công!");
        return "redirect:/admin/sanpham";
    }
}
