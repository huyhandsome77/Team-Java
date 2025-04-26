package uth.edu.jpa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uth.edu.jpa.models.BookingModel;
import uth.edu.jpa.models.QLSModel;
import uth.edu.jpa.repositories.QLSRepository;
import uth.edu.jpa.services.BookingService;

import java.util.List;

@Controller
@RequestMapping("/Coach")

public class CoachController {
    @Autowired
    private QLSRepository qlsRepository;
    @Autowired
    private BookingService bookingService;


    @GetMapping("/dashboard")
    public String dashboard( Model model) {
        List<BookingModel> bookings = bookingService.getAllBookings();
        model.addAttribute("bookings", bookings);
        return "Coach/Coach_Dashboard";
    }

    @GetMapping("/datsan")
    public String showCourts(Model model) {
//        List<QLSModel> dsSan = qlsRepository.findAll();
//        model.addAttribute("dsSan", dsSan);
        return "Coach/Coach_DatSan";
    }


    @PostMapping("/deleteBooking/{id}")
    public String deleteBooking(@PathVariable("id") Long id, Model model) {
        bookingService.deleteBookingById(id);  // Xóa booking qua service
        model.addAttribute("message", "Xóa booking thành công!");
        return "redirect:/Coach/dashboard";
    }


    @GetMapping("/tournament")
    public String tournaments(Model model) {
        model.addAttribute("coachName", "Huấn Luyện Viên");
        return "Coach/Coach_Tournament";
    }

    @GetMapping("/chat")
    public String chats(Model model) {
        model.addAttribute("coachName", "Huấn Luyện Viên");
        return "Coach/Coach_Chat";
    }
}