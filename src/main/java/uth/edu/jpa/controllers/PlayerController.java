package uth.edu.jpa.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uth.edu.jpa.config.SecurityUtils;
import uth.edu.jpa.models.DuBaoThoiTiet;
import uth.edu.jpa.models.LienHe;
import uth.edu.jpa.models.SanPham;
import uth.edu.jpa.models.User;
import uth.edu.jpa.repositories.LienHeRepository;
import uth.edu.jpa.repositories.SanPhamRepository;
import uth.edu.jpa.repositories.UserRepository;
import uth.edu.jpa.services.CartService;
import uth.edu.jpa.services.WeatherService;


import java.util.List;

@Controller
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Autowired
    private LienHeRepository lienHeRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private WeatherService weatherService;


    @GetMapping("/home")
    public String PlayerDashboardPage(Model model, HttpSession session) {
        User user = SecurityUtils.getCurrentUser();
        int quantity = cartService.getTotalQuantity(user);
        model.addAttribute("soLuongTrongGio", quantity);
        return "player/Player_Dashboard";
    }
    @GetMapping("/datsan")
    public String PlayerDatSanPage() {
        return "player/Player_DatSan"; // gọi đến html page
    }

    @GetMapping("/lienhe")
    public String PlayerLienHePage(Model model) {
        model.addAttribute("lienHe", new LienHe()); // Khởi tạo form
        return "player/Player_LienHe";
    }

    @PostMapping("/lienhe")
    public String xuLyLienHe(@ModelAttribute("lienHe") LienHe lienHe, RedirectAttributes redirectAttributes) {
        lienHeRepository.save(lienHe);
        redirectAttributes.addFlashAttribute("thongBao", "Gửi liên hệ thành công! Chúng tôi sẽ phản hồi sớm.");
        return "redirect:/player/lienhe";
    }
    @GetMapping("/du-bao")
    public String PlayerDuBaoThoiTiet(@RequestParam(name = "city", defaultValue = "Ho Chi Minh") String city, Model model) {
        List<DuBaoThoiTiet> forecast = weatherService.layDuLieuThoiTietTuAPI(city);
        WeatherService.AIPrediction aiPrediction = weatherService.predictTemperatureTrend(city);

        model.addAttribute("forecast", forecast);
        model.addAttribute("aiPrediction", aiPrediction);
        model.addAttribute("city", city);

        return "player/Player_DuBaoThoiTiet";
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

    @ModelAttribute("soLuongTrongGio")
    public int getSoLuongTrongGio() {
        User user = SecurityUtils.getCurrentUser();
        if (user != null) {
            return cartService.getTotalQuantity(user);  // Hàm này bạn phải implement trong CartService
        }
        return 0;
    }
}


