package uth.edu.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uth.edu.jpa.models.Support;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import uth.edu.jpa.models.LienHe;


@Repository
public interface SupportRepository extends JpaRepository<Support, Long> {
    // Tìm tất cả yêu cầu hỗ trợ liên quan đến một lienHe
    List<Support> findByLienHe(LienHe lienHe);

    // Tìm yêu cầu hỗ trợ theo ID
    Optional<Support> findById(Long id);

    // Tìm bản ghi hỗ trợ mới nhất theo thời gian tạo
    Optional<Support> findFirstByOrderByCreatedAtDesc(); // Phương thức tìm Support mới nhất
}
