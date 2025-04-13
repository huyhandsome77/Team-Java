package uth.edu.jpa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uth.edu.jpa.models.*;
import uth.edu.jpa.repositories.*;
import uth.edu.jpa.services.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/dashboard")
    public String AdminPage() {
        return "admin/admin_dashboard"; // gọi đến html page
    }
    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/quanlibanhang")
    public String adminQuanlybanhang(Model model) {
        List<SanPham> danhSachSanPham = sanPhamService.findAll();  // Lấy danh sách sản phẩm từ service
        model.addAttribute("sanPhamList", danhSachSanPham);  // Thêm danh sách sản phẩm vào model

        return "admin/admin_salesmanagement";  // Trả về view
    }
    @GetMapping("/quanlitaikhoan")
    public String adminQuanlytaikhoan(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "admin/admin_accountmanagement"; // gọi đến html page
    }


}