package uth.edu.jpa.services;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uth.edu.jpa.models.DuBaoThoiTiet;
import uth.edu.jpa.repositories.DuBaoThoiTietRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherService {

    @Autowired
    private DuBaoThoiTietRepository repository;

    private final String API_KEY = "bebd71451d7a3c0addcac4f001a6c1d5";
    private final String FORECAST_URL = "https://api.openweathermap.org/data/2.5/forecast";

    public List<DuBaoThoiTiet> layDuLieuThoiTietTuAPI(String city) {
        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusDays(5);
        List<DuBaoThoiTiet> cachedData = repository.findByCityAndNgayBetween(city, today, endDate);
        if (!cachedData.isEmpty()) {
            return cachedData;
        }

        try {
            String url = String.format("%s?q=%s&appid=%s&units=metric", FORECAST_URL, city, API_KEY);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            Gson gson = new Gson();
            JsonObject json = gson.fromJson(response.getBody(), JsonObject.class);
            JsonArray list = json.getAsJsonArray("list");

            List<DuBaoThoiTiet> forecast = new ArrayList<>();
            for (int i = 0; i < list.size() && forecast.size() < 5; i++) {
                JsonObject item = list.get(i).getAsJsonObject();
                LocalDate date = LocalDate.parse(item.get("dt_txt").getAsString().substring(0, 10));
                if (date.isBefore(today) || date.isAfter(endDate)) continue;

                DuBaoThoiTiet duLieu = new DuBaoThoiTiet();
                duLieu.setCity(city);
                duLieu.setNgay(date);
                duLieu.setNhietDo(item.getAsJsonObject("main").get("temp").getAsFloat());
                duLieu.setDoAm(item.getAsJsonObject("main").get("humidity").getAsFloat());
                duLieu.setTocDoGio(item.getAsJsonObject("wind").get("speed").getAsFloat() * 3.6f);
                duLieu.setDuBaoAi(item.getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString());
                duLieu.setPrecipitation(item.has("pop") ? item.get("pop").getAsFloat() * 100 : 0.0f);
                forecast.add(duLieu);
                repository.save(duLieu);
            }
            return forecast;
        } catch (Exception e) {
            System.err.println("Lỗi khi gọi API thời tiết: " + e.getMessage());
            return new ArrayList<>(); // Trả về danh sách rỗng nếu lỗi
        }
    }

    public AIPrediction predictTemperatureTrend(String city) {
        List<DuBaoThoiTiet> historicalData = repository.findByCityAndNgayBetween(
                city, LocalDate.now().minusDays(10), LocalDate.now()
        );

        // Thuật toán AI: Hồi quy tuyến tính để dự đoán nhiệt độ
        Float[] temperatures = historicalData.stream().map(DuBaoThoiTiet::getNhietDo).toArray(Float[]::new);
        if (temperatures.length < 2) {
            return new AIPrediction("Không đủ dữ liệu", 0.0f);
        }

        // Tính xu hướng (slope) bằng hồi quy tuyến tính
        double sumX = 0, sumY = 0, sumXY = 0, sumXX = 0;
        int n = temperatures.length;
        for (int i = 0; i < n; i++) {
            sumX += i;
            sumY += temperatures[i];
            sumXY += i * temperatures[i];
            sumXX += i * i;
        }
        double slope = (n * sumXY - sumX * sumY) / (n * sumXX - sumX * sumX);
        String trend = slope > 0 ? "Tăng" : slope < 0 ? "Giảm" : "Ổn định";
        float predictedTemp = temperatures[n - 1] + (float) slope;

        return new AIPrediction(trend, predictedTemp);
    }

    public static class AIPrediction {
        private String trend;
        private float predictedTemperature;

        public AIPrediction(String trend, float predictedTemperature) {
            this.trend = trend;
            this.predictedTemperature = predictedTemperature;
        }

        public String getTrend() { return trend; }
        public float getPredictedTemperature() { return predictedTemperature; }
    }
}