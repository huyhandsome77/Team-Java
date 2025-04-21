package uth.edu.jpa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import uth.edu.jpa.models.QLSModel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import uth.edu.jpa.repositories.QLSRepository;

@Controller

public class QLSController {
    @Autowired
    private QLSRepository qlsRepository;
    @ModelAttribute("court")
    public QLSModel getCourtModel() {
        return new QLSModel();
    }

    @GetMapping("/QuanLySan")
    public String QuanLySanPage(Model model) {
        List<QLSModel> dsSan = qlsRepository.findAll();
        model.addAttribute("dsSan", dsSan);
        model.addAttribute("court", new QLSModel());
        return "QuanLySan/QuanLySan_Dashboard";
    }

    @GetMapping("/Booking")
    public String BookingPage(Model model) {
        return "QuanLySan/Booking";
    }

    @PostMapping("/Booking/Confirm")
    public String confirmBooking(@RequestParam("bookingId") Long bookingId) {
        return "redirect:/Booking";
    }

    @PostMapping("/Booking/Cancel")
    public String cancelBooking(@RequestParam("bookingId") Long bookingId) {
        return "redirect:/Booking";
    }

    @GetMapping("/History")
    public String HistoryPage() { return "QuanLySan/History"; }
    @GetMapping("Support")
    public String SupportPage() { return "QuanLySan/Support"; }

    @GetMapping("/Chart")
    public String ChartPage() { return "QuanLySan/Chart"; }

    @GetMapping("/list")
    public String showCourts(Model model) {
        List<QLSModel> dsSan = qlsRepository.findAll();
        model.addAttribute("dsSan", dsSan);
        return "QuanLySan/QuanLySan_Dashboard";
    }

    // Xử lý thêm sân mới
    @PostMapping("/add")
    public String addCourt(@ModelAttribute QLSModel court,
                           @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            if (!imageFile.isEmpty()) {
                String fileName = imageFile.getOriginalFilename();
                String uploadDir = System.getProperty("user.dir") + "/Uploads";
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                // Lưu file
                Path filePath = uploadPath.resolve(fileName);
                imageFile.transferTo(filePath.toFile());
                court.setImage("/Uploads/" + fileName);
            }
            qlsRepository.save(court);
        } catch (IOException e) {
            e.printStackTrace(); // hoặc log lỗi
        }
        return "redirect:/QuanLySan";
    }



    // Hiển thị form cập nhật sân
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<QLSModel> optionalCourt = qlsRepository.findById(id);
        if (optionalCourt.isPresent()) {
            model.addAttribute("court", optionalCourt.get());
            return "QuanLySan/QuanLySan_Dashboard";
        }
        return "redirect:/QuanLySan";
    }

    // Xử lý cập nhật status
    @PostMapping("/edit/{id}")
    public String updateCourtStatus(@PathVariable Long id, @RequestParam("status") String status) {
        Optional<QLSModel> optionalCourt = qlsRepository.findById(id);
        if (optionalCourt.isPresent()) {
            QLSModel court = optionalCourt.get();
            court.setStatus(status);
            qlsRepository.save(court);
        }
        return "redirect:/QuanLySan";
    }


    // Xử lý xóa sân
    @GetMapping("/delete/{id}")
    public String deleteCourt(@PathVariable Long id) {
        qlsRepository.deleteById(id);
        return "redirect:/QuanLySan";
    }


}


