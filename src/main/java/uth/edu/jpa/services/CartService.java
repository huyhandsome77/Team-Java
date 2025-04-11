package uth.edu.jpa.services;

import uth.edu.jpa.models.CartItem;
import uth.edu.jpa.models.SanPham;

import java.util.List;

public interface CartService {
    void themSanPham(SanPham sanPham, int soLuong);
    void capNhatSoLuong(Long cartItemId, int soLuong);
    void xoaSanPham(Long cartItemId);
    List<CartItem> layDanhSachGioHang();
    int tinhTongTien();
}
