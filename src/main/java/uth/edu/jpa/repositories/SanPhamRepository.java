package uth.edu.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uth.edu.jpa.models.SanPham;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, Long> {
}
