package uth.edu.jpa.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uth.edu.jpa.DTO.BookingRequest;
import uth.edu.jpa.models.BookingModel;
import uth.edu.jpa.models.DanhGia;
import uth.edu.jpa.models.QLSModel;
import uth.edu.jpa.repositories.DanhGiaRepository;
import uth.edu.jpa.repositories.QLSRepository;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import uth.edu.jpa.services.BookingService;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private QLSRepository qlsRepository;
    @Autowired
    private DanhGiaRepository danhGiaRepository;

    @GetMapping("/player")
    public String PlayerDashboardPage() {
        return "player/Player_Dashboard"; // gọi đến html page
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
        // Lấy sân từ repository
        QLSModel court = qlsRepository.findById(courtId).orElse(null);
        if (court == null) {
            model.addAttribute("error", "Sân không tồn tại.");
            return "redirect:/datsan";
        }

        // Kiểm tra khung giờ trùng
        boolean trungGio = bookingService.kiemTraTrungGio(court, booking.getBookingDate(), booking.getStartTime(), booking.getEndTime());

        if (trungGio) {
            model.addAttribute("error", "Khung giờ bạn chọn đã có người đặt!");
            return "redirect:/datsan";
        }

        // Set lại sân vào booking và lưu
        booking.setCourt(court);
        bookingService.save(booking);

        redirectAttributes.addFlashAttribute("successMessage", "Bạn đã đặt sân thành công!");

        return "redirect:/datsan?success=true";
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
        // Truyền flash attribute để hiển thị modal cảm ơn
        redirectAttributes.addFlashAttribute("showThanks", "true");
        return "redirect:/datsan";
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
