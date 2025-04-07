package uth.edu.jpa.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/player")
public class PlayerController {
    @GetMapping("/home")
    public String PlayerDashboardPage(HttpServletRequest request) {
        return "player/Player_Dashboard" ; // gọi đến html page
    }
    @GetMapping("/datsan")
    public String PlayerDatSanPage() {
        return "player/Player_DatSan"; // gọi đến html page
    }
    @GetMapping("/lienhe")
    public String PlayerLienHePage() {
        return "player/Player_LienHe"; // gọi đến html page
    }
    @GetMapping("/muasanpham")
    public String PlayerMuaSPPage() {
        return "player/Player_MuaSanPham"; // gọi đến html page
    }
    @GetMapping("/nhacnho")
    public String PlayerNhacNho() {
        return "player/Player_NhacNho"; // gọi đến html page
    }
}


