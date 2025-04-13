package uth.edu.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uth.edu.jpa.models.NotificationModels;
import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepositories extends JpaRepository<NotificationModels, Integer> {

    Optional<NotificationModels> findByNotificationId(Integer NotificationId);

    // Các phương thức hữu ích
    List<NotificationModels> findByisRead(boolean isRead); // Tìm thông báo theo trạng thái đã đọc/chưa đọc


}