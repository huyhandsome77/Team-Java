package uth.edu.jpa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uth.edu.jpa.models.User;
import uth.edu.jpa.services.CartService;
import uth.edu.jpa.config.SecurityUtils;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * Thêm sản phẩm vào giỏ hàng
     */
    @PostMapping("/add/{id}")
    public String addToCart(@PathVariable("id") Long sanPhamId) {
        System.out.println("Adding product with ID: " + sanPhamId);
        User user = SecurityUtils.getCurrentUser();
        cartService.addToCart(user, sanPhamId);
        return "redirect:/cart";
    }

    /**
     * Hiển thị giỏ hàng của người dùng
     */
    @GetMapping
    public String viewCart(Model model) {
        User user = SecurityUtils.getCurrentUser();
        model.addAttribute("cartItems", cartService.getCartItems(user));
        model.addAttribute("total", cartService.getTotal(user));
        return "/player/Player_GioHang";
    }

    /**
     * Xoá sản phẩm khỏi giỏ
     */
    @PostMapping("/remove/{id}")
    public String removeFromCart(@PathVariable("id") Long sanPhamId) {
        User user = SecurityUtils.getCurrentUser();
        cartService.removeFromCart(user, sanPhamId);
        return "redirect:/cart";
    }

    /**
     * Cập nhật số lượng sản phẩm
     */
    @PostMapping("/update/{id}")
    public String updateQuantity(@PathVariable("id") Long sanPhamId,
                                 @RequestParam("quantity") int quantity) {
        User user = SecurityUtils.getCurrentUser();
        cartService.updateQuantity(user, sanPhamId, quantity);
        return "redirect:/cart";
    }
}
