package thinh.example.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thinh.example.jpa.models.Notification;
import thinh.example.jpa.repositories.NotificationRepositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class notificationservice {
    @Autowired
    private NotificationRepositories notificationRepositories;

    // Lấy thông báo theo ID
    public Optional<Notification> getByNotificationId(int notificationId) {
        return notificationRepositories.findByNotificationId(notificationId);
    }

    // Lấy danh sách thông báo chưa đọc
    public List<Notification> getUnreadNotifications() {
        return notificationRepositories.findByisRead(false);
    }

    // Lấy danh sách thông báo đã đọc
    public List<Notification> getReadNotifications() {
        return notificationRepositories.findByisRead(true);
    }


    // Tạo mới một thông báo
    public Notification createNotification(String message) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setIsRead(false); // Mặc định là chưa đọc
        notification.setCreatedAt(LocalDateTime.now()); // Thời gian hiện tại
        return notificationRepositories.save(notification);
    }

    // Cập nhật trạng thái đã đọc của thông báo
    public Notification markAsRead(int notificationId) {
        Optional<Notification> optionalNotification = notificationRepositories.findByNotificationId(notificationId);
        if (optionalNotification.isPresent()) {
            Notification notification = optionalNotification.get();
            notification.setIsRead(true);
            return notificationRepositories.save(notification);
        }
        return null;
    }

    // Xóa thông báo theo ID
    public boolean deleteNotification(int notificationId) {
        Optional<Notification> optionalNotification = notificationRepositories.findByNotificationId(notificationId);
        if (optionalNotification.isPresent()) {
            notificationRepositories.delete(optionalNotification.get());
            return true;
        }
        return false;
    }
}