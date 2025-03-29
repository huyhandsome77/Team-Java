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
    private CourtRepository courtRepository; // Thêm CourtRepository

    public Booking saveBooking(Booking booking) {
        if (booking.getStartTime() != null) {
            booking.setEndTime(booking.getStartTime().plusHours(1));
        }
        return bookingRepository.save(booking);
    }

    // Thêm phương thức getAllCourts để lấy danh sách sân
    public List<Court> getAllCourts() {
        return courtRepository.findAll();
    }
}