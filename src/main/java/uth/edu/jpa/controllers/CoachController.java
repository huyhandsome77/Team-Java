package uth.edu.jpa.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/coach")
public class CoachController {

    @GetMapping("/home")
    public String coachHome() {
        return "master/coach-home"; // file templates/coach-home.html
    }
}
