package uth.edu.jpa.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uth.edu.jpa.models.DanhGia;
import uth.edu.jpa.models.QLSModel;
import uth.edu.jpa.repositories.DanhGiaRepository;
import uth.edu.jpa.repositories.QLSRepository;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;

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


