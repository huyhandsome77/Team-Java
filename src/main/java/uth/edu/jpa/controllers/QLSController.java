package uth.edu.jpa.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QLSController {

    @GetMapping("/QuanLySan")
    public String QuanLySanPage() {
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

    @GetMapping("/Status")
    public String StatusPage() { return "QuanLySan/Status"; }

    @GetMapping("/History")
    public String HistoryPage() { return "QuanLySan/History"; }

    @GetMapping("/Chart")
    public String ChartPage() { return "QuanLySan/Chart"; }
}
