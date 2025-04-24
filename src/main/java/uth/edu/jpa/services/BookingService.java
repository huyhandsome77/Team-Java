package uth.edu.jpa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uth.edu.jpa.models.Booking;
import uth.edu.jpa.models.Court;
import uth.edu.jpa.repositories.BookingRepository;
import uth.edu.jpa.repositories.CourtRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CourtRepository courtRepository;

    /**
     * Đặt sân Pickleball mới.
     * @param courtId ID sân
     * @param date Ngày đặt
     * @param startTime Giờ bắt đầu
     * @return Booking đã lưu hoặc null nếu sân không tồn tại hoặc sân đã được đặt
     */
    public Booking bookCourt(Long courtId, LocalDate date, LocalTime startTime) {
        // Kiểm tra xem sân có tồn tại
        Optional<Court> optionalCourt = courtRepository.findById(courtId);
        if (optionalCourt.isPresent()) {
            Court court = optionalCourt.get();

            // Kiểm tra xem sân có đang bận vào giờ này không
            boolean isBooked = bookingRepository.existsByCourtAndDateAndStartTime(court, date, startTime);
            if (isBooked) {
                return null; // Sân đã được đặt
            }

            // Tạo một booking mới
            Booking booking = new Booking();
            booking.setCourt(court);
            booking.setDate(date);
            booking.setStartTime(startTime);
            booking.setEndTime(startTime.plusHours(1)); // Tự động tính giờ kết thúc (1 tiếng)
            return bookingRepository.save(booking);
        }
        return null; // Sân không tồn tại
    }

    /**
     * Lưu booking (dùng khi chỉnh sửa hoặc tạo mới).
     */
    public Booking saveBooking(Booking booking) {
        if (booking.getStartTime() != null && booking.getEndTime() == null) {
            booking.setEndTime(booking.getStartTime().plusHours(1)); // Tính giờ kết thúc nếu chưa có
        }
        return bookingRepository.save(booking);
    }

    /**
     * Lấy tất cả các lượt đặt sân.
     */
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    /**
     * Xóa một booking theo ID.
     */
    public boolean deleteBookingById(Long id) {
        if (bookingRepository.existsById(id)) {
            bookingRepository.deleteById(id);
            return true;
        }
        return false;
    }


    /**
     * Lấy danh sách các đặt sân theo sân, ngày và giờ.
     */
    public List<Booking> getBookingsByCourtAndDate(Long courtId, LocalDate date) {
        Optional<Court> optionalCourt = courtRepository.findById(courtId);
        if (optionalCourt.isPresent()) {
            Court court = optionalCourt.get();
            return bookingRepository.findByCourtAndDate(court, date);
        }
        return null;
    }

    /**
     * Kiểm tra xem sân đã được đặt vào thời gian này chưa.
     */
    public boolean isCourtBooked(Long courtId, LocalDate date, LocalTime startTime) {
        Optional<Court> optionalCourt = courtRepository.findById(courtId);
        if (optionalCourt.isPresent()) {
            Court court = optionalCourt.get();
            return bookingRepository.existsByCourtAndDateAndStartTime(court, date, startTime);
        }
        return false;
    }
}
