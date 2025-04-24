package uth.edu.jpa.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uth.edu.jpa.config.SecurityUtils;
import uth.edu.jpa.models.User;
import uth.edu.jpa.repositories.CartRepository;
import uth.edu.jpa.services.CartService;

@Controller
public class HomeController {
    @Autowired
    private CartService cartService;
    @GetMapping(value = {"/", "/home"})
    public String homepage(Model model, HttpSession session) {
        User user = SecurityUtils.getCurrentUser();
        int quantity = cartService.getTotalQuantity(user);
        model.addAttribute("soLuongTrongGio", quantity);
        return "player/Player_Dashboard";
    }
}


