package uth.edu.jpa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uth.edu.jpa.DTO.BookingRequest;
import uth.edu.jpa.models.BookingModel;
import uth.edu.jpa.models.QLSModel;
import uth.edu.jpa.repositories.BookingRepository;
import uth.edu.jpa.repositories.QLSRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class BookingService {
@Autowired
private QLSRepository qlsRepository;
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
    public void saveBookingRequest(BookingRequest bookingRequest) {
        BookingModel bookingModel = new BookingModel();

        bookingModel.setFullName(bookingRequest.getFullName());
        bookingModel.setPhoneNumber(bookingRequest.getPhoneNumber());
        bookingModel.setBookingDate(bookingRequest.getBookingDate());
        bookingModel.setStartTime(bookingRequest.getStartTime());
        bookingModel.setEndTime(bookingRequest.getEndTime());
        bookingModel.setNote(bookingRequest.getNote());


        QLSModel court = qlsRepository.findById(bookingRequest.getCourtId())
                .orElseThrow(() -> new RuntimeException("Court not found"));
        bookingModel.setCourt(court);

        bookingModel.setStatus("Chờ duyệt");

        bookingRepository.save(bookingModel);
    }

}