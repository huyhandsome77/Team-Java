package uth.edu.jpa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import uth.edu.jpa.models.*;
import org.springframework.security.core.Authentication;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import uth.edu.jpa.services.PlayerService;
import uth.edu.jpa.services.SupportService;
import uth.edu.jpa.DTO.SupportMessageDTO;
import uth.edu.jpa.repositories.BookingRepository;
import uth.edu.jpa.repositories.QLSRepository;
import uth.edu.jpa.repositories.SupportRepository;
import uth.edu.jpa.repositories.LienHeRepository;
import uth.edu.jpa.repositories.UserRepository;

@Controller
public class QLSController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QLSRepository qlsRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private SupportService supportService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private LienHeRepository lienHeRepository;

    @Autowired
    private SupportRepository supportMessageRepository;

    @Autowired
    private SupportRepository supportRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

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
        // Xử lý xác nhận đặt sân
        return "redirect:/Booking";
    }

    @PostMapping("/Booking/Cancel")
    public String cancelBooking(@RequestParam("bookingId") Long bookingId) {
        // Xử lý hủy đặt sân
        return "redirect:/Booking";
    }

    @GetMapping("/History")
    public String HistoryPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
        List<BookingModel> historyBookings = bookingRepository.findByUserId(user.getUserID());
        Set<QLSModel> uniqueCourtsSet = historyBookings.stream()
                .map(BookingModel::getCourt)
                .collect(Collectors.toSet());
        List<QLSModel> uniqueCourts = new ArrayList<>(uniqueCourtsSet);
        uniqueCourts.sort((court1, court2) -> Long.compare(court1.getCourtId(), court2.getCourtId()));
        model.addAttribute("historyBookings", historyBookings);
        model.addAttribute("uniqueCourts", uniqueCourts);
        return "QuanLySan/History";
    }

    @GetMapping("/Support")
    public String supportPage(Model model) {
        try {
            List<LienHe> requests = lienHeRepository.findAll();
            model.addAttribute("requests", requests);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Đã xảy ra lỗi khi lấy yêu cầu hỗ trợ.");
        }
        return "QuanLySan/Support";
    }

    // Xử lý yêu cầu hỗ trợ - đánh dấu đã xử lý
    @PostMapping("/Support/resolve/{id}")
    public String resolveSupportRequest(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            LienHe lienHe = lienHeRepository.findById(id).orElseThrow(() -> new RuntimeException("Yêu cầu không tồn tại"));
            lienHe.setResolved(true);
            lienHeRepository.save(lienHe);

            // Thêm thông báo thành công vào model khi redirect
            redirectAttributes.addFlashAttribute("success", "Yêu cầu đã được xử lý thành công.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Đã xảy ra lỗi khi xử lý yêu cầu.");
        }
        return "redirect:/Support";
    }

    // Xóa yêu cầu hỗ trợ đã xử lý
    @PostMapping("/Support/delete/{id}")
    public String deleteSupportRequest(@PathVariable("id") Long id) {
        try {
            LienHe lienHe = lienHeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Yêu cầu không tồn tại"));
            if (lienHe.isResolved()) {
                lienHeRepository.delete(lienHe);
            } else {
                // Nếu yêu cầu chưa được xử lý, bạn có thể thêm thông báo lỗi hoặc làm gì đó
                // Ví dụ: throw new RuntimeException("Không thể xóa yêu cầu chưa xử lý");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/Support";
    }


    @GetMapping("/Chart")
    public String ChartPage() {
        return "QuanLySan/Chart";
    }

    @GetMapping("/list")
    public String showCourts(Model model) {
        List<QLSModel> dsSan = qlsRepository.findAll();
        model.addAttribute("dsSan", dsSan);
        return "QuanLySan/QuanLySan_Dashboard";
    }

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
                Path filePath = uploadPath.resolve(fileName);
                imageFile.transferTo(filePath.toFile());
                court.setImage("/Uploads/" + fileName);
            }
            qlsRepository.save(court);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/QuanLySan";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<QLSModel> optionalCourt = qlsRepository.findById(id);
        if (optionalCourt.isPresent()) {
            model.addAttribute("court", optionalCourt.get());
            return "QuanLySan/QuanLySan_Dashboard";
        }
        model.addAttribute("error", "Không tìm thấy sân.");
        return "redirect:/QuanLySan";
    }

    @PostMapping("/edit/{id}")
    public String updateCourtStatus(@PathVariable Long id,
                                    @RequestParam("status") String status,
                                    @RequestParam("price") double price) {
        Optional<QLSModel> optionalCourt = qlsRepository.findById(id);
        if (optionalCourt.isPresent()) {
            QLSModel court = optionalCourt.get();
            court.setStatus(status);
            court.setRentalPrice(price);
            qlsRepository.save(court);
        }
        return "redirect:/QuanLySan";
    }

    @GetMapping("/delete/{id}")
    public String deleteCourt(@PathVariable Long id) {
        qlsRepository.deleteById(id);
        return "redirect:/QuanLySan";
    }
}
