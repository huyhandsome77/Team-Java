package uth.edu.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uth.edu.jpa.models.Booking;
import uth.edu.jpa.models.Court;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Kiểm tra xem sân có đã được đặt vào thời gian cụ thể không
    boolean existsByCourtAndDateAndStartTime(Court court, LocalDate date, LocalTime startTime);

    // Lấy danh sách booking theo sân và ngày
    List<Booking> findByCourtAndDate(Court court, LocalDate date);
}
