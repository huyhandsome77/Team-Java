package uth.edu.jpa.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uth.edu.jpa.models.Field;
import uth.edu.jpa.models.User;
import uth.edu.jpa.repositories.FieldRepository;

import java.util.List;

@Repository
public interface FieldRepository extends  JpaRepository<Field,Long> {
    // Tìm kiếm sân bóng theo tên
    List<Field> findByNameContaining(String name);
    // Tìm kiếm các sân còn trống
    List<Field> findByIsAvailableTrue();
}
