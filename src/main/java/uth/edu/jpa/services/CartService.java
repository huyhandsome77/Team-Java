package uth.edu.jpa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uth.edu.jpa.models.*;
import uth.edu.jpa.repositories.*;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private CartItemRepository cartItemRepo;

    @Autowired
    private SanPhamRepository sanPhamRepo;

    // Lấy hoặc tạo giỏ hàng mới
    @Transactional
    public Cart getOrCreateCart(User user) {
        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cartRepo.save(cart);
        }
        return cart;
    }

    // Thêm sản phẩm vào giỏ hàng
    @Transactional
    public void addToCart(User user, Long sanPhamId) {
        Cart cart = getOrCreateCart(user);
        Optional<CartItemEntity> optional = cart.getItems().stream()
                .filter(item -> item.getSanPham().getMaSanPham() == sanPhamId)
                .findFirst();

        if (optional.isPresent()) {
            CartItemEntity item = optional.get();
            item.setSoLuong(item.getSoLuong() + 1);
        } else {
            SanPham sp = sanPhamRepo.findById(sanPhamId).orElseThrow();
            CartItemEntity item = new CartItemEntity();
            item.setSanPham(sp);
            item.setSoLuong(1); // Thêm số lượng ban đầu là 1
            item.setCart(cart);
            cart.getItems().add(item);
        }

        cartRepo.save(cart);  // Lưu giỏ hàng sau khi thay đổi
    }

    // Xoá sản phẩm khỏi giỏ hàng
    @Transactional
    public void removeFromCart(User user, Long sanPhamId) {
        Cart cart = getOrCreateCart(user);
        Optional<CartItemEntity> optional = cart.getItems().stream()
                .filter(item -> item.getSanPham().getMaSanPham() == sanPhamId)
                .findFirst();

        optional.ifPresent(item -> {
            cart.getItems().remove(item);
            cartItemRepo.delete(item); // Xoá sản phẩm khỏi CartItemRepository
        });

        cartRepo.save(cart);  // Lưu lại giỏ hàng sau khi xóa
    }

    // Cập nhật số lượng sản phẩm trong giỏ hàng
    @Transactional
    public void updateQuantity(User user, Long sanPhamId, int quantity) {
        if (quantity < 1) {
            return; // Không cho phép số lượng dưới 1
        }

        Cart cart = getOrCreateCart(user);
        cart.getItems().forEach(item -> {
            if (item.getSanPham().getMaSanPham() == sanPhamId) {
                item.setSoLuong(quantity); // Cập nhật số lượng
            }
        });
        cartRepo.save(cart);  // Lưu lại giỏ hàng sau khi cập nhật
    }

    // Lấy tất cả sản phẩm trong giỏ hàng
    public List<CartItemEntity> getCartItems(User user) {
        return getOrCreateCart(user).getItems();
    }

    // Tính tổng giá trị giỏ hàng
    public double getTotal(User user) {
        return getOrCreateCart(user).getItems().stream()
                .mapToDouble(item -> item.getSanPham().getGia() * item.getSoLuong()) // Tính tổng giá trị
                .sum();
    }
}
