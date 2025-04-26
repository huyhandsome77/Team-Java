package uth.edu.jpa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uth.edu.jpa.models.BookingModel;
import uth.edu.jpa.models.QLSModel;
import uth.edu.jpa.repositories.BookingRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public boolean kiemTraTrungGio(QLSModel court, LocalDate date, LocalTime start, LocalTime end) {
        List<BookingModel> danhSach = bookingRepository.findByCourtAndBookingDate(court, date);

        for (BookingModel booking : danhSach) {
            if (start.isBefore(booking.getEndTime()) && end.isAfter(booking.getStartTime())) {
                return true;
            }
        }
        return false;
    }

    public void save(BookingModel booking) {
        bookingRepository.save(booking);
    }

    public List<BookingModel> getAllBookings() {
        return bookingRepository.findAll();
    }
    public long getTotalBookings() {
        return bookingRepository.count();
    }

    public void deleteBookingById(Long id) {
        bookingRepository.deleteById(id);
    }
    public void saveBooking(BookingModel bookingModel) {
        bookingRepository.save(bookingModel);
    }

}