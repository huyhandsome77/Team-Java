package uth.edu.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uth.edu.jpa.models.Booking;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    // Lấy các đặt sân theo ngày
    List<Booking> findByDate(LocalDate date);

    // Lấy các đặt sân sắp tới
    @Query("SELECT b FROM Booking b WHERE b.date >= CURRENT_DATE ORDER BY b.date ASC, b.startTime ASC")
    List<Booking> findUpcomingBookings();

    // Lấy các đặt sân gần đây
    @Query("SELECT b FROM Booking b ORDER BY b.date DESC, b.startTime DESC")
    List<Booking> findRecentBookings();
}