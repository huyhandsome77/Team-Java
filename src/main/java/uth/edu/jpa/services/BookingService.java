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

    // Sửa phương thức kiemTraTrungGio để sử dụng đối tượng court thay vì courtId
    public boolean kiemTraTrungGio(QLSModel court, LocalDate date, LocalTime start, LocalTime end) {
        List<BookingModel> danhSach = bookingRepository.findByCourtAndBookingDate(court, date);

        for (BookingModel booking : danhSach) {
            // Kiểm tra nếu thời gian đặt sân có giao nhau
            if (booking.getStartTime().isBefore(end) && start.isBefore(booking.getEndTime())) {
                return true; // có giao nhau
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
}
