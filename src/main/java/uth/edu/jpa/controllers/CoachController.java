package uth.edu.jpa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uth.edu.jpa.models.BookingModel;
import uth.edu.jpa.models.QLSModel;
import uth.edu.jpa.models.User;
import uth.edu.jpa.repositories.QLSRepository;
import uth.edu.jpa.repositories.UserRepository;
import uth.edu.jpa.services.BookingService;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/Coach")


public class CoachController {
    @Autowired
    private UserRepository userRepository;
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
        List<QLSModel> dsSan = qlsRepository.findAll();
        List<BookingModel> historyBookings = bookingService.getAllBookings();
        model.addAttribute("dsSan", dsSan);
        model.addAttribute("historyBookings", historyBookings);
        model.addAttribute("bookingModel", new BookingModel());
        return "Coach/Coach_DatSan";
    }
    @GetMapping("/datsan/{courtId}")
    public String showBookingForm(@PathVariable Long courtId, Model model) {
        QLSModel san = qlsRepository.findById(courtId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sân"));
        model.addAttribute("san", san);
        model.addAttribute("bookingModel", new BookingModel());
        return "Coach/Coach_DatSan";
    }
    @PostMapping("/datsan")
    public String handleBooking(
            @RequestParam Long courtId,
            @RequestParam String fullName,
            @RequestParam String phoneNumber,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate bookingDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime,
            @RequestParam(required = false) String note,
            @RequestParam double totalPrice,
            Principal principal,
            RedirectAttributes ra
    ) {
        QLSModel san = qlsRepository.findById(courtId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sân"));
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));
        BookingModel booking = new BookingModel();
        booking.setCourt(san);
        booking.setUser(user);
        booking.setFullName(fullName);
        booking.setPhoneNumber(phoneNumber);
        booking.setBookingDate(bookingDate);
        booking.setStartTime(startTime);
        booking.setEndTime(endTime);
        booking.setNote(note);
        booking.setTotalPrice(totalPrice);
        bookingService.save(booking);
        ra.addFlashAttribute("message", "Đặt sân thành công!");
        return "redirect:/Coach/datsan";
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