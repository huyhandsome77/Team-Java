package uth.edu.jpa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uth.edu.jpa.models.Booking;
import uth.edu.jpa.models.Court;
import uth.edu.jpa.repositories.BookingRepository;
import uth.edu.jpa.repositories.CourtRepository;

import java.time.LocalTime;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CourtRepository courtRepository;

    // Lưu booking vào database
    public Booking saveBooking(Booking booking) {
        if (booking.getStartTime() != null) {
            booking.setEndTime(booking.getStartTime().plusHours(1)); // Tự động tính giờ kết thúc
        }
        return bookingRepository.save(booking);
    }

    // Lấy danh sách tất cả sân
    public List<Court> getAllCourts() {
        return courtRepository.findAll();
    }

    // Lấy danh sách tất cả booking
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // Xóa booking theo ID
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}