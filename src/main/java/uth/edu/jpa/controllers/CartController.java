package uth.edu.jpa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uth.edu.jpa.models.*;
import uth.edu.jpa.repositories.*;
import uth.edu.jpa.services.*;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @PostMapping("/them-vao-gio")
    public String themVaoGio(@RequestParam int maSanPham, @RequestParam int soLuong) {
        SanPham sp = sanPhamRepository.findById(maSanPham).orElseThrow();
        cartService.themSanPham(sp, soLuong);
        return "redirect:/gio-hang";
    }

    @PostMapping("/cap-nhat-so-luong")
    public String capNhat(@RequestParam int maSanPham, @RequestParam int soLuong) {
        cartService.capNhatSoLuong((long)maSanPham, soLuong);
        return "redirect:/gio-hang";
    }

    @PostMapping("/xoa-khoi-gio")
    public String xoaKhoiGio(@RequestParam int maSanPham) {
        cartService.xoaSanPham((long)maSanPham);
        return "redirect:/gio-hang";
    }

    @GetMapping("/gio-hang")
    public String hienThiGioHang(Model model) {
        model.addAttribute("dsGioHang", cartService.layDanhSachGioHang());
        model.addAttribute("tongTien", cartService.tinhTongTien());
        return "cart/giohang";
    }
}
