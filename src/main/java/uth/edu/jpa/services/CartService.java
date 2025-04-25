package uth.edu.jpa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uth.edu.jpa.models.*;
import uth.edu.jpa.repositories.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class CartService {

    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private CartItemRepository cartItemRepo;

    @Autowired
    private SanPhamRepository sanPhamRepo;

    public Cart getCartByUser(User user) {
        return cartRepo.findByUser(user).orElse(null);
    }

    public int getTotalQuantity(User user) {
        Optional<Cart> optionalCart = cartRepo.findByUser(user);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            List<CartItemEntity> items = cart.getItems(); // Lấy danh sách sản phẩm
            return items.stream().mapToInt(CartItemEntity::getSoLuong).sum();
        }
        return 0;
    }


    // Lấy hoặc tạo giỏ hàng mới
    @Transactional
    public Cart getOrCreateCart(User user) {
        return cartRepo.findByUser(user).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            user.setCart(newCart); // << THÊM DÒNG NÀY
            return cartRepo.save(newCart);
        });
    }

    // Thêm sản phẩm vào giỏ hàng
    @Transactional
    public void addToCart(User user, Long sanPhamId) {
        Cart cart = getOrCreateCart(user);
        Optional<CartItemEntity> optional = cart.getItems().stream()
                .filter(item -> Objects.equals(item.getSanPham().getMaSanPham(), sanPhamId))
                .findFirst();

        if (optional.isPresent()) {
            CartItemEntity item = optional.get();
            item.setSoLuong(item.getSoLuong() + 1);
        } else {
            SanPham sp = sanPhamRepo.findById(sanPhamId).orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại với ID: " + sanPhamId));
            CartItemEntity item = new CartItemEntity();
            item.setSanPham(sp);
            item.setSoLuong(1);
            item.setCart(cart);
            cart.getItems().add(item);
        }

        cartRepo.save(cart);
    }

    // Xoá sản phẩm khỏi giỏ hàng
    @Transactional
    public void removeFromCart(User user, Long sanPhamId) {
        Cart cart = getOrCreateCart(user);
        Optional<CartItemEntity> optional = cart.getItems().stream()
                .filter(item -> Objects.equals(item.getSanPham().getMaSanPham(), sanPhamId))
                .findFirst();

        optional.ifPresent(item -> {
            cart.getItems().remove(item);
            cartItemRepo.delete(item);
        });

        cartRepo.save(cart);
    }

    // Cập nhật số lượng sản phẩm trong giỏ hàng
    @Transactional
    public void updateQuantity(User user, Long sanPhamId, int quantity) {
        if (quantity < 1) {
            return;
        }

        Cart cart = getOrCreateCart(user);
        cart.getItems().forEach(item -> {
            if (Objects.equals(item.getSanPham().getMaSanPham(), sanPhamId)) {
                item.setSoLuong(quantity);
            }
        });
        cartRepo.save(cart);
    }

    // Lấy tất cả sản phẩm trong giỏ hàng
    public List<CartItemEntity> getCartItems(User user) {
        return getOrCreateCart(user).getItems();
    }

    // Tính tổng giá trị giỏ hàng
    public double getTotal(User user) {
        return getOrCreateCart(user).getItems().stream()
                .mapToDouble(item -> item.getSanPham().getGia() * item.getSoLuong())
                .sum();
    }

    // Xoá toàn bộ sản phẩm trong giỏ hàng
    @Transactional
    public void clearCart(User user) {
        Cart cart = getOrCreateCart(user);
        List<CartItemEntity> items = cart.getItems();

        // Xoá từng item trong giỏ hàng khỏi database
        for (CartItemEntity item : items) {
            cartItemRepo.delete(item);
        }

        // Xoá hết khỏi danh sách trong cart
        items.clear();

        cartRepo.save(cart);
    }

}
