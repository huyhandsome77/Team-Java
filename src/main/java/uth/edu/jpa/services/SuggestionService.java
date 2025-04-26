//package uth.edu.jpa.services;
//
//import uth.edu.jpa.models.WeatherInfo;
//import org.springframework.stereotype.Service;
//
//@Service
//public class SuggestionService {
//
//    public String suggestClothing(WeatherInfo weather) {
//        double temp = weather.getTemperature();
//        String description = weather.getWeatherDescription();
//
//        if (description.equalsIgnoreCase("Rain")) {
//            return "Mang áo mưa, giày chống nước, quần áo nhanh khô.";
//        }
//
//        if (temp >= 30) {
//            return "Áo thun mát, quần short thể thao, nước lạnh.";
//        } else if (temp >= 20) {
//            return "Áo thể thao, quần dài, nước thường.";
//        } else {
//            return "Áo khoác nhẹ, quần dài, trà nóng.";
//        }
//    }
//}