package uth.edu.jpa.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uth.edu.jpa.config.SecurityUtils;
import uth.edu.jpa.models.*;
import uth.edu.jpa.repositories.*;
import uth.edu.jpa.services.BookingService;
import uth.edu.jpa.services.CartService;


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
    private UserRepository userRepository;

    @Autowired
    private QLSRepository qlsRepository;

    @Autowired
    private DanhGiaRepository danhGiaRepository;


    @GetMapping("/home")
    public String PlayerDashboardPage(Model model, HttpSession session) {
        User user = SecurityUtils.getCurrentUser();
        int quantity = cartService.getTotalQuantity(user);
        model.addAttribute("soLuongTrongGio", quantity);
        return "player/Player_Dashboard";
    }
    @GetMapping("/datsan")
    public String showCourts(Model model) {
        List<QLSModel> dsSan = qlsRepository.findAll();
        model.addAttribute("dsSan", dsSan);
        return "player/Player_DatSan";
    }

    @Autowired
    private BookingService bookingService;

    @PostMapping("/datsan/{courtId}")
    public String datSan(@RequestParam Long courtId,
                         @ModelAttribute BookingModel booking, Model model,
                         RedirectAttributes redirectAttributes) {


        QLSModel court = qlsRepository.findById(courtId).orElse(null);
        if (court == null) {
            model.addAttribute("error", "Sân không tồn tại.");
            return "redirect:/player/datsan";
        }

        boolean trungGio = bookingService.kiemTraTrungGio(court, booking.getBookingDate(), booking.getStartTime(), booking.getEndTime());
        if (trungGio) {
            redirectAttributes.addFlashAttribute("error", "Khung giờ bạn chọn đã có người đặt!");
            return "redirect:/player/datsan?success=false";
        }

        long durationInMinutes = java.time.Duration.between(booking.getStartTime(), booking.getEndTime()).toMinutes();
        double rentalPrice = court.getRentalPrice();
        double totalPrice = (durationInMinutes / 60.0) * rentalPrice;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy thông tin người dùng.");
            return "redirect:/player/datsan";
        }
        booking.setUser(user);
        booking.setTotalPrice(totalPrice);
        booking.setCourt(court);
        bookingService.save(booking);
        redirectAttributes.addFlashAttribute("successMessage", "Bạn đã đặt sân thành công!");
        return "redirect:/player/datsan?success=true";
    }


    @PostMapping("/danhgia/{courtId}")
    public String danhGiaSan(@PathVariable Long courtId,
                             @RequestParam("rating") int rating,
                             @RequestParam("comment") String comment,
                             RedirectAttributes redirectAttributes) {
        QLSModel court = qlsRepository.findById(courtId).orElse(null);
        if (court != null) {
            DanhGia dg = new DanhGia();
            dg.setSoSao(rating);
            dg.setComment(comment);
            dg.setCourt(court);
            danhGiaRepository.save(dg);
        }

        redirectAttributes.addFlashAttribute("showThanks", "true");
        return "redirect:/player/datsan";
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


    @GetMapping("/nhacnho")
    public String PlayerNhacNho() {
        return "player/Player_NhacNho"; // gọi đến html page
    }
    @GetMapping("/muasanpham")
    public String PlayerMuaSPPage(Model model) {
        List<SanPham> dsSanPham = sanPhamRepository.findAll();
        model.addAttribute("dsSanPham", dsSanPham);
        return "player/Player_MuaSanPham";
    }

    @ModelAttribute("soLuongTrongGio")
    public int getSoLuongTrongGio() {
        User user = SecurityUtils.getCurrentUser();
        if (user != null) {
            return cartService.getTotalQuantity(user);
        }
        return 0;
    }
}

