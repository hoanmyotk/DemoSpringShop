package com.example.demo.controllers;

import com.example.demo.models.HoaDon;
import com.example.demo.models.ItemCart;
import com.example.demo.models.KhachHang;
import com.example.demo.services.ChiTietHoaDonService;
import com.example.demo.services.HoaDonService;
import com.example.demo.services.ItemCartService;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/cart")
public class CartController {

    private final ItemCartService cartService;
    @Autowired
    private HoaDonService hoaDonService;

    @Autowired
    private ChiTietHoaDonService chiTietHoaDonService;

    @Autowired // Đảm bảo Spring sẽ tự động inject ItemCartService
    public CartController(ItemCartService cartService) {
        this.cartService = cartService;
    }

    // Hiển thị giỏ hàng
    @RequestMapping
    public String viewCart(HttpSession session, Model model) {
        List<ItemCart> cartItems = (List<ItemCart>) session.getAttribute("cartItems");
        if (cartItems == null) {
        	cartService.clearCart();
            session.setAttribute("cartItems", cartItems);
        }
        else {
        	cartItems = cartService.getCartItems();
            session.setAttribute("cartItems", cartItems);
		}
        session.setAttribute("totalQuantityItems", cartService.totalQuantityItems());
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", cartService.calculateTotal());
        return "cart";
    }

    // Thêm sản phẩm vào giỏ hàng
    @PostMapping("/add")
    public String addToCart(
            @RequestParam("maSP") String maSP,
            @RequestParam("tenSP") String tenSP,
            @RequestParam("gia") long gia,
            @RequestParam("soLuong") int soLuong,
            @RequestParam("anh") String anh,
            HttpSession session) {
    	if (session.getAttribute("cartItems") == null) {
    		cartService.clearCart();
		}
        cartService.addItem(maSP, tenSP, gia, soLuong, anh);
        session.setAttribute("totalQuantityItems", cartService.totalQuantityItems());
        session.setAttribute("cartItems", cartService.getCartItems());
        return "redirect:/store"; 
    }

    @PostMapping("/manage")
    public String manageCart(
            @RequestParam(value = "action", required = false) String action,
            @RequestParam(value = "selectedItems", required = false) List<String> selectedItems,
            @RequestParam(value = "productId", required = false) List<String> productIds,
            @RequestParam(value = "quantity", required = false) List<Integer> quantities,
            HttpSession session) {

        System.out.println("ManageCart Endpoint Hit");
        System.out.println("Action: " + action);
        System.out.println("Selected Items: " + (selectedItems != null ? selectedItems : "No items selected"));
        System.out.println("Product IDs: " + productIds);
        System.out.println("Quantities: " + quantities);

        if (action == null || action.isEmpty()) {
            System.out.println("Action is null or empty.");
            return "redirect:/cart";
        }

        if ("deleteSelected".equals(action)) {
            if (selectedItems != null && !selectedItems.isEmpty()) {
                for (String maSP : selectedItems) {
                    cartService.removeItem(maSP);
                }
            }
        } else if ("updateQuantity".equals(action)) {
            if (productIds != null && quantities != null && productIds.size() == quantities.size()) {
                for (int i = 0; i < productIds.size(); i++) {
                    cartService.updateQuantity(productIds.get(i), quantities.get(i));
                }
            }
        }
        session.setAttribute("cartItems", cartService.getCartItems());
        return "redirect:/cart";
    }



    // Xóa toàn bộ giỏ hàng
    @PostMapping("/clear")
    public String clearCart(HttpSession session) {
        cartService.clearCart();
        session.setAttribute("cartItems", cartService.getCartItems());
        return "redirect:/cart";
    }
    // Xác nhận giỏ hàng
    @GetMapping("/confirm")
    public String confirmCart(HttpSession session) {
        // Lấy danh sách sản phẩm từ session
    	List<ItemCart> cartItems = (List<ItemCart>) session.getAttribute("cartItems");

        if (cartItems == null || cartItems.isEmpty()) {
            // Nếu giỏ hàng trống, chuyển hướng về trang giỏ hàng
            return "redirect:/cart";
        }

        // Lấy mã khách hàng từ session hoặc mặc định nếu chưa đăng nhập
        KhachHang khachhang = (KhachHang)session.getAttribute("khachHang");
        if (khachhang == null) {
        	return "redirect:/form-sign-in-up";
        }

        // Tạo hóa đơn
        HoaDon hoaDon = hoaDonService.createHoaDon(khachhang.getMaKH(), false);

        // Lưu chi tiết hóa đơn
        for (ItemCart item : cartItems) {
            String maSP = (String) item.getMaSP();
            int soLuong = item.getSoLuong();

            // Tạo chi tiết hóa đơn
            chiTietHoaDonService.createChiTietHoaDon(hoaDon.getMaHD(), maSP, soLuong);
        }

        // Xóa giỏ hàng sau khi xác nhận
        session.removeAttribute("cartItems");
        cartService.clearCart();

        // Chuyển hướng về trang xác nhận
        return "redirect:/cart";
    }
}
