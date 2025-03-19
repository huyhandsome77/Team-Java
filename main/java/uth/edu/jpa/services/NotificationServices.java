package uth.edu.jpa.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uth.edu.jpa.models.NotificationModels;
import uth.edu.jpa.repositories.NotificationRepositories;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationServices {
    @Autowired
    private NotificationRepositories notificationRepositories;

    // Lấy thông báo theo ID
    public Optional<NotificationModels> getByNotificationId(int notificationId) {
        return notificationRepositories.findByNotificationId(notificationId);
    }

    // Lấy danh sách thông báo chưa đọc
    public List<NotificationModels> getUnreadNotifications() {
        return notificationRepositories.findByisRead(false);
    }

    // Lấy danh sách thông báo đã đọc
    public List<NotificationModels> getReadNotifications() {
        return notificationRepositories.findByisRead(true);
    }


    // Tạo mới một thông báo
    public NotificationModels createNotification(String message) {
        NotificationModels notificationModels = new NotificationModels();
        notificationModels.setMessage(message);
        notificationModels.setIsRead(false); // Mặc định là chưa đọc
        notificationModels.getCreatedAt(LocalDateTime.now()); // Thời gian hiện tại
        return notificationRepositories.save(notificationModels);
    }

    // Cập nhật trạng thái đã đọc của thông báo
    public NotificationModels markAsRead(int notificationId) {
        Optional<NotificationModels> optionalNotification = notificationRepositories.findByNotificationId(notificationId);
        if (optionalNotification.isPresent()) {
            NotificationModels notificationModels = optionalNotification.get();
            notificationModels.setIsRead(true);
            return notificationRepositories.save(notificationModels);
        }
        return null;
    }

    // Xóa thông báo theo ID
    public boolean deleteNotification(int notificationId) {
        Optional<NotificationModels> optionalNotification = notificationRepositories.findByNotificationId(notificationId);
        if (optionalNotification.isPresent()) {
            notificationRepositories.delete(optionalNotification.get());
            return true;
        }
        return false;
    }
}