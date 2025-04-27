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
    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderService OrderService;
    @Autowired
    private BookingService bookingService;

    @GetMapping("/dashboard")
    public String AdminPage(Model model) {
        long soSanDaDat = bookingService.getTotalBookings();
        model.addAttribute("soSanDaDat", soSanDaDat);
        return "admin/admin_dashboard";
    }

    @GetMapping("/quanlibanhang")
    public String adminQuanlybanhang(Model model) {
        List<SanPham> danhSachSanPham = sanPhamService.findAll();  // Lấy danh sách sản phẩm từ service
        model.addAttribute("sanPhamList", danhSachSanPham);  // Thêm danh sách sản phẩm vào model
        List<Order> orders = OrderService.getAllOrders();
        long TongTien = OrderService.getTotalOrders();
        model.addAttribute("Tongtien", TongTien);
        model.addAttribute("orders", orders);
        return "admin/admin_salesmanagement";  // Trả về view
    }
    @GetMapping("/quanlitaikhoan")
    public String adminQuanlytaikhoan(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "admin/admin_accountmanagement"; // gọi đến html page
    }


}



