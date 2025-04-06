package uth.edu.jpa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uth.edu.jpa.services.BookingService;

@Controller
@RequestMapping("/coach")

public class CoachController {

    @Autowired
    private BookingService bookingService;

<<<<<<< HEAD
    @GetMapping("/dashboard")
=======
    @GetMapping("/Coach_Dashboard")
>>>>>>> 04ba37b8d6b41a15fa522a5aef59bda557646e0a
    public String dashboard(Model model) {
        model.addAttribute("coachName", "Huấn Luyện Viên");
        return "coach/Coach_Dashboard";
    }

    @GetMapping("/datsan")
    public String datSan(Model model) {

        // Không cần truyền booking và courts nữa
        return "coach/Coach_Datsan";
    }

    @PostMapping("/datsan")
    public String submitBooking(Model model) {
        // Tạm thời không xử lý form, chỉ hiển thị thông báo
        model.addAttribute("message", "Chức năng đặt sân đã bị tạm ngưng!");
        model.addAttribute("coachName", "Huấn Luyện Viên");
        return "coach/datsan";
    }

    @GetMapping("/players")
    public String players(Model model) {
        model.addAttribute("coachName", "Huấn Luyện Viên");
        return "coach/Coach_Players";
    }

    @GetMapping("/tournament")
    public String tournaments(Model model) {
        model.addAttribute("coachName", "Huấn Luyện Viên");
        return "coach/Coach_Tournament";
    }
<<<<<<< HEAD
    @GetMapping("/chat")
    public String chat(Model model) {
        model.addAttribute("coachName","Huấn Luyện Viên");
        return "coach/Coach_Chat";
    }

=======
>>>>>>> 04ba37b8d6b41a15fa522a5aef59bda557646e0a
}