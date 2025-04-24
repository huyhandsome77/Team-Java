package uth.edu.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uth.edu.jpa.models.DuBaoThoiTiet;
import java.time.LocalDate;
import java.util.List;

public interface DuBaoThoiTietRepository extends JpaRepository<DuBaoThoiTiet, Long> {
    List<DuBaoThoiTiet> findByCityAndNgayBetween(String city, LocalDate startDate, LocalDate endDate);
}