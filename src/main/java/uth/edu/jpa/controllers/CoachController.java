package uth.edu.jpa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/coach")
public class CoachController {
    @GetMapping("/dashboard")
    public String dashboard() {
        return "Coach/Coach_Dashboard";
    }

    @GetMapping("/datsan")
    public String datSan() {
        return "coach/Coach_DatSan";
    }

//    @PostMapping("/deleteBooking/{id}")
//    public String deleteBooking(@PathVariable("id") Long id, Model model) {
//        return "coach/Coach_DatSan";
//    }


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