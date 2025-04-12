<<<<<<< HEAD
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
=======
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
<<<<<<< HEAD
    @GetMapping("/dashboard")
=======
    @GetMapping("/Coach_Dashboard")
>>>>>>> 04ba37b8d6b41a15fa522a5aef59bda557646e0a
>>>>>>> 4468b5d3f2506f4fb70cdb0fd95bdbf9b15f5678
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
=======
<<<<<<< HEAD
>>>>>>> 4468b5d3f2506f4fb70cdb0fd95bdbf9b15f5678
    @GetMapping("/chat")
    public String chat(Model model) {
        model.addAttribute("coachName","Huấn Luyện Viên");
        return "coach/Coach_Chat";
    }

<<<<<<< HEAD
=======
=======
>>>>>>> 04ba37b8d6b41a15fa522a5aef59bda557646e0a
>>>>>>> 4468b5d3f2506f4fb70cdb0fd95bdbf9b15f5678
>>>>>>> 30e5f160a636709e3a4cc54e413c955712659e2f
}