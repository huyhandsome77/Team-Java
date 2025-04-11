package uth.edu.jpa.services.impl;

import org.springframework.stereotype.Service;
import uth.edu.jpa.models.CartItem;
import uth.edu.jpa.models.SanPham;
import uth.edu.jpa.services.CartService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final List<CartItem> gioHang = new ArrayList<>();

    @Override
    public void themSanPham(SanPham sanPham, int soLuong) {
        for (CartItem item : gioHang) {
            if (item.getSanPham().getMaSanPham() == sanPham.getMaSanPham()) {
                item.setSoLuong(item.getSoLuong() + soLuong);
                return;
            }
        }
        gioHang.add(new CartItem(sanPham, soLuong));
    }

    @Override
    public void capNhatSoLuong(Long cartItemId, int soLuong) {
        for (CartItem item : gioHang) {
            if (item.getId() != null && item.getId().equals(cartItemId)) {
                item.setSoLuong(soLuong);
                return;
            }
        }
    }

    @Override
    public void xoaSanPham(Long cartItemId) {
        gioHang.removeIf(item -> item.getId() != null && item.getId().equals(cartItemId));
    }

    @Override
    public List<CartItem> layDanhSachGioHang() {
        return gioHang;
    }

    @Override
    public int tinhTongTien() {
        // Nếu muốn làm tròn sang int
        return (int) gioHang.stream()
                .mapToDouble(CartItem::getTongTien)
                .sum();
    }
}
