package uth.edu.jpa.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/player")
    public String PlayerDashboardPage() {
        return "player/Player_Dashboard"; // gọi đến html page
    }
    @GetMapping("/datsan")
    public String PlayerDatSanPage() {
        return "player/Player_DatSan"; // gọi đến html page
    }
    @GetMapping("/lienhe")
    public String PlayerLienHePage() {
        return "player/Player_LienHe"; // gọi đến html page
    }

}


