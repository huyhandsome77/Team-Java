    package uth.edu.jpa.controllers;

    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.stereotype.Controller;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.servlet.mvc.support.RedirectAttributes;
    import uth.edu.jpa.DTO.BookingRequest;
    import uth.edu.jpa.models.BookingModel;
    import uth.edu.jpa.models.DanhGia;
    import uth.edu.jpa.models.QLSModel;
    import uth.edu.jpa.models.User;
    import uth.edu.jpa.repositories.DanhGiaRepository;
    import uth.edu.jpa.repositories.QLSRepository;
    import org.springframework.ui.Model;
    import org.springframework.beans.factory.annotation.Autowired;
    import uth.edu.jpa.repositories.UserRepository;
    import uth.edu.jpa.services.BookingService;

    import java.util.List;

    @Controller
    public class HomeController {
        @Autowired
        private UserRepository userRepository;
        @Autowired
        private QLSRepository qlsRepository;
        @Autowired
        private DanhGiaRepository danhGiaRepository;

        @GetMapping("/player")
        public String PlayerDashboardPage() {
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
                return "redirect:/datsan";
            }

            boolean trungGio = bookingService.kiemTraTrungGio(court, booking.getBookingDate(), booking.getStartTime(), booking.getEndTime());
            if (trungGio) {
                redirectAttributes.addFlashAttribute("error", "Khung giờ bạn chọn đã có người đặt!");
                return "redirect:/datsan?success=false";
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
                return "redirect:/datsan";
            }
            booking.setUser(user);
            booking.setTotalPrice(totalPrice);
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
