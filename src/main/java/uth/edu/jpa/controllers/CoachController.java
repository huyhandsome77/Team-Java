package uth.edu.jpa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uth.edu.jpa.models.Booking;
import uth.edu.jpa.models.Court;
import uth.edu.jpa.models.Player;
import uth.edu.jpa.services.BookingService;
import uth.edu.jpa.repositories.PlayerRepository;

import java.util.List;

@Controller
@RequestMapping("/coach")
public class CoachController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private PlayerRepository playerRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("coachName", "Huấn Luyện Viên");
        return "coach/Coach_Dashboard";
    }

    @GetMapping("/datsan")
    public String datSan(Model model) {
        model.addAttribute("coachName", "Huấn Luyện Viên");
        model.addAttribute("booking", new Booking());
        List<Court> courts = bookingService.getAllCourts();
        model.addAttribute("courts", courts);
        List<Booking> bookings = bookingService.getAllBookings();
        model.addAttribute("bookings", bookings);
        return "coach/Coach_DatSan";
    }

    @PostMapping("/datsan")
    public String submitBooking(@ModelAttribute("booking") Booking booking, Model model) {
        try {
            // Gán Player cho Booking
            Player player = playerRepository.findById(1L)
                    .orElseThrow(() -> new RuntimeException("Player không tồn tại"));
            booking.setPlayer(player); // Gọi phương thức setPlayer

            bookingService.saveBooking(booking);
            model.addAttribute("message", "Đặt sân thành công!");
        } catch (Exception e) {
            model.addAttribute("message", "Lỗi khi đặt sân: " + e.getMessage());
        }
        model.addAttribute("coachName", "Huấn Luyện Viên");
        model.addAttribute("booking", new Booking());
        List<Court> courts = bookingService.getAllCourts();
        model.addAttribute("courts", courts);
        List<Booking> bookings = bookingService.getAllBookings();
        model.addAttribute("bookings", bookings);
        return "coach/Coach_DatSan";
    }

    @PostMapping("/deleteBooking/{id}")
    public String deleteBooking(@PathVariable("id") Long id, Model model) {
        bookingService.deleteBooking(id);
        model.addAttribute("message", "Xóa đặt sân thành công!");
        model.addAttribute("coachName", "Huấn Luyện Viên");
        model.addAttribute("booking", new Booking());
        List<Court> courts = bookingService.getAllCourts();
        model.addAttribute("courts", courts);
        List<Booking> bookings = bookingService.getAllBookings();
        model.addAttribute("bookings", bookings);
        return "coach/Coach_DatSan";
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

    @GetMapping("/chat")
    public String chats(Model model) {
        model.addAttribute("coachName", "Huấn Luyện Viên");
        return "coach/Coach_Chat";
    }

}