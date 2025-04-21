package uth.edu.jpa.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uth.edu.jpa.models.QLSModel;
import uth.edu.jpa.models.SanPham;
import uth.edu.jpa.repositories.SanPhamRepository;
import uth.edu.jpa.repositories.QLSRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/player")
public class PlayerController {
    @Autowired
    private QLSRepository qlsRepository;

    private SanPhamRepository sanPhamRepository;

    @GetMapping("/home")
    public String PlayerDashboardPage(HttpServletRequest request) {
        return "player/Player_Dashboard" ; // gọi đến html page
    }



    @GetMapping("/lienhe")
    public String PlayerLienHePage() {
        return "player/Player_LienHe"; // gọi đến html page
    }
//    @GetMapping("/muasanpham")
//    public String PlayerMuaSPPage() {
//        return "player/Player_MuaSanPham"; // gọi đến html page
//    }
    @GetMapping("/nhacnho")
    public String PlayerNhacNho() {
        return "player/Player_NhacNho"; // gọi đến html page
    }

    @GetMapping("/muasanpham")
    public String PlayerMuaSPPage(Model model) {
        List<SanPham> dsSanPham = sanPhamRepository.findAll(); // hoặc bất cứ service nào bạn dùng
        model.addAttribute("dsSanPham", dsSanPham);
        return "player/Player_MuaSanPham"; // gọi đến html page
    }
    @GetMapping("/giohang")
    public String PlayerGioHangPage() {
        return "player/Player_GioHang"; // gọi đến html page
    }
}


