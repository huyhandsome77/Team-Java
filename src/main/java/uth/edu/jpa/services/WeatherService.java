package uth.edu.jpa.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import uth.edu.jpa.models.WeatherInfo;
import uth.edu.jpa.models.WeatherResponse;

import java.time.LocalDate;
import java.util.Objects;

@Service
public class WeatherService {

    private static final String API_KEY = "d22e320245ca4f83aa9140546252604";
    private static final String API_URL = "https://api.weatherapi.com/v1/forecast.json";

    private final RestTemplate restTemplate = new RestTemplate();

    public WeatherInfo getWeatherForecast(String city, LocalDate date) {
        String apiUrl = API_URL + "?key=" + API_KEY + "&q=" + city + "&days=1";
        try {
            WeatherResponse response = restTemplate.getForObject(apiUrl, WeatherResponse.class);
            if (response != null && response.getLocation() != null) {
                WeatherInfo info = new WeatherInfo();
                info.setDate(date);
                info.setTemperature(response.getForecast().getForecastday().get(0).getDay().getAvgtempC());
                info.setWeatherDescription(response.getForecast().getForecastday().get(0).getDay().getCondition().getText());
                info.setAiSuggestion(generateAISuggestion(info));
                return info;
            } else {
                throw new RuntimeException("Không tìm thấy thông tin thời tiết cho thành phố này.");
            }
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Lỗi kết nối với dịch vụ thời tiết: " + e.getMessage());
        }
    }

    private String generateAISuggestion(WeatherInfo info) {
        String description = Objects.requireNonNullElse(info.getWeatherDescription(), "").toLowerCase();
        double temp = info.getTemperature();
        StringBuilder suggestion = new StringBuilder();

        if (description.contains("rain") || description.contains("shower") || description.contains("storm")) {
            suggestion.append("Mang theo ô ☔ và mặc áo mưa.");
        } else if (description.contains("sunny") || description.contains("clear")) {
            suggestion.append("Đeo kính râm 😎 và mặc đồ mát.");
        } else if (description.contains("cloud")) {
            suggestion.append("Mặc áo khoác nhẹ 🌥️.");
        } else if (description.contains("snow")) {
            suggestion.append("Mặc áo ấm ❄️ và uống cacao nóng.");
        } else {
            suggestion.append("Ăn mặc bình thường.");
        }

        if (temp >= 30) {
            suggestion.append(" Uống nước mát 🍹.");
        } else if (temp <= 15) {
            suggestion.append(" Uống trà nóng ☕.");
        } else {
            suggestion.append(" Uống nước lọc.");
        }

        return suggestion.toString();
    }
}
