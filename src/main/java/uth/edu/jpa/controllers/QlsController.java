package uth.edu.jpa.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QlsController {

    @GetMapping("/QuanLySan")
    public String QuanLySanPage() {
        return "QuanLySan/QuanLySan_Dashboard";
    }
}
