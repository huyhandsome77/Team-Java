package uth.edu.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uth.edu.jpa.models.BookingModel;
import uth.edu.jpa.models.QLSModel;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<BookingModel, Long> {
    List<BookingModel> findByCourtAndBookingDate(QLSModel court, LocalDate bookingDate);

}
