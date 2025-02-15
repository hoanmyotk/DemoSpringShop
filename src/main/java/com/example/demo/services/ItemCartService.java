package com.example.demo.services;

import org.springframework.stereotype.Service;
import com.example.demo.models.ItemCart;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCartService {
    private final List<ItemCart> cartItems = new ArrayList<>();

    // Lấy danh sách sản phẩm trong giỏ hàng
    public List<ItemCart> getCartItems() {
        return cartItems;
    }

    // Thêm sản phẩm vào giỏ hàng
    public void addItem(String maSP, String tenSP, long gia, int soLuong, String anh) {
        for (ItemCart item : cartItems) {
            if (item.getMaSP().equals(maSP)) {
                // Nếu sản phẩm đã tồn tại, tăng số lượng
                item.setSoLuong(item.getSoLuong() + soLuong);
                return;
            }
        }
        // Nếu sản phẩm chưa có, thêm mới
        cartItems.add(new ItemCart(maSP, tenSP, gia, soLuong, anh));
    }

    // Xóa sản phẩm khỏi giỏ hàng
    public void removeItem(String maSP) {
        cartItems.removeIf(item -> item.getMaSP().equals(maSP));
    }

    // Tính tổng giá trị giỏ hàng
    public long calculateTotal() {
        long total = 0;
        for (ItemCart item : cartItems) {
            total += item.getThanhTien();
        }
        return total;
    }
    public long totalQuantityItems() {
        long total = 0;
        for (ItemCart item : cartItems) {
            total += item.getSoLuong();
        }
        return total;
    }

    // Cập nhật số lượng sản phẩm
    public void updateQuantity(String maSP, int soLuong) {
        for (ItemCart item : cartItems) {
            if (item.getMaSP().equals(maSP)) {
                item.setSoLuong(soLuong);
                return;
            }
        }
    }

    // Xóa tất cả sản phẩm trong giỏ hàng
    public void clearCart() {
        cartItems.clear();
    }
}
