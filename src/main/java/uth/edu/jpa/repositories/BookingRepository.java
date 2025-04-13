package uth.edu.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uth.edu.jpa.models.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
