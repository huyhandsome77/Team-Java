package uth.edu.jpa.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uth.edu.jpa.models.SupportRequest;
import org.springframework.beans.factory.annotation.Autowired;
import uth.edu.jpa.repositories.SupportRepository;


import java.time.LocalDateTime;
import java.util.Optional;


@Controller
@RequestMapping("/support")
public class SupportController {

    @Autowired
    private SupportRepository supportRepository;

    // Form cho người dùng gửi yêu cầu hỗ trợ
    @GetMapping("/form")
    public String showSupportForm(Model model) {
        model.addAttribute("supportRequest", new SupportRequest());
        return "support/form";
    }

    @PostMapping("/submit")
    public String submitSupportRequest(@ModelAttribute SupportRequest request) {
        request.setCreatedAt(LocalDateTime.now());
        request.setResolved(false);
        supportRepository.save(request);
        return "redirect:/support/thank-you";
    }

    @GetMapping("/thank-you")
    public String showThankYouPage() {
        return "support/thank-you";
    }

    // Dành cho admin: xem danh sách yêu cầu hỗ trợ
    @GetMapping("/admin")
    public String viewAllSupportRequests(Model model) {
        model.addAttribute("requests", supportRepository.findAllByOrderByCreatedAtDesc());
        return "support/admin-list";
    }

    @PostMapping("/resolve/{id}")
    public String resolveRequest(@PathVariable Long id) {
        Optional<SupportRequest> optional = supportRepository.findById(id);
        if (optional.isPresent()) {
            SupportRequest req = optional.get();
            req.setResolved(true);
            supportRepository.save(req);
        }
        return "redirect:/support/admin";
    }
}
