package uth.edu.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uth.edu.jpa.models.DanhGia;
import uth.edu.jpa.models.QLSModel;

import java.util.List;

@Repository
public interface DanhGiaRepository extends JpaRepository<DanhGia, Long> {
    List<DanhGia> findByCourt(QLSModel court);
}