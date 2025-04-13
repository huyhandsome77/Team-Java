package uth.edu.jpa.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/dashboard")
    public String AdminPage() {
        return "admin/admin_dashboard"; // gọi đến html page
    }
    @GetMapping("/quanlibanhang")
    public String adminQuanlybanhang() {
        return "admin/admin_salesmanagement"; // gọi đến html page
    }
    @GetMapping("/quanlitaikhoan")
    public String adminQuanlytaikhoan() {
        return "admin/admin_accountmanagement"; // gọi đến html page
    }


}



