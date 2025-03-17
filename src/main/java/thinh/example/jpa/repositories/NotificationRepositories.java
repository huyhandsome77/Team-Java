package thinh.example.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import thinh.example.jpa.models.Notification;
import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepositories extends JpaRepository<Notification, Integer> {

    Optional<Notification> findByNotificationId(Integer NotificationId);

    // Các phương thức hữu ích
    List<Notification> findByisRead(boolean isRead); // Tìm thông báo theo trạng thái đã đọc/chưa đọc
    List<Notification> findByCreatedAtAfter(java.time.LocalDateTime dateTime); // Tìm thông báo sau một thời điểm
    List<Notification> findAllByOrderByCreatedAtDesc(); // Lấy tất cả thông báo, sắp xếp theo thời gian giảm dần
}