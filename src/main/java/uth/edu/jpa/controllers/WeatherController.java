package uth.edu.jpa.controllers;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uth.edu.jpa.models.WeatherInfo;
import uth.edu.jpa.services.WeatherService;

import java.time.LocalDate;

@Controller
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    public String weatherForm() {
        return "chat";
    }

    @PostMapping("/forecast")
    public String getForecast(@RequestParam String city,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                              Model model) {
        WeatherInfo info = weatherService.getWeatherForecast(city, date);

        if (info != null) {
            model.addAttribute("weather", info);
        } else {
            model.addAttribute("error", "Không tìm thấy dự báo cho ngày đó 😢");
        }

        return "chat";
    }
}
