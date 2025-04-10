package uth.edu.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uth.edu.jpa.models.QLSModel;
import java.util.List;

@Repository
public interface QLSRepository extends JpaRepository<QLSModel, Long> {
    List<QLSModel> findByStatus(String status);
}
