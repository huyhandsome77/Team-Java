package uth.edu.jpa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public Cart getOrCreateCart(User user) {
        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cartRepo.save(cart);
        }
        return cart;
    }

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
            item.setSoLuong(1);
            item.setCart(cart);
            cart.getItems().add(item);
        }

        cartRepo.save(cart);
    }

    public void removeFromCart(User user, Long sanPhamId) {
        Cart cart = getOrCreateCart(user);
        cart.getItems().removeIf(item -> item.getSanPham().getMaSanPham() == sanPhamId);
        cartRepo.save(cart);
    }

    public void updateQuantity(User user, Long sanPhamId, int quantity) {
        Cart cart = getOrCreateCart(user);
        cart.getItems().forEach(item -> {
            if (item.getSanPham().getMaSanPham() == sanPhamId) {
                item.setSoLuong(quantity);
            }
        });
        cartRepo.save(cart);
    }

    public List<CartItemEntity> getCartItems(User user) {
        return getOrCreateCart(user).getItems();
    }

    public double getTotal(User user) {
        return getOrCreateCart(user).getTotal();
    }
}
