package thinh.example.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import thinh.example.jpa.models.Notification;
import thinh.example.jpa.service.notificationservice;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final notificationservice notificationService;

    @Autowired
    public NotificationController(notificationservice notificationService) {
        this.notificationService = notificationService;
    }

    // API lấy thông báo theo ID
    @GetMapping("/{id}")
    public Optional<Notification> getNotificationById(@PathVariable int id) {
        return notificationService.getByNotificationId(id);
    }

    // API lấy danh sách thông báo chưa đọc
    @GetMapping("/unread")
    public List<Notification> getUnreadNotifications() {
        return notificationService.getUnreadNotifications();
    }

    // API lấy danh sách thông báo đã đọc
    @GetMapping("/read")
    public List<Notification> getReadNotifications() {
        return notificationService.getReadNotifications();
    }


    // API tạo mới một thông báo
    @PostMapping("/create")
    public Notification createNotification(@RequestParam String message) {
        return notificationService.createNotification(message);
    }

    // API đánh dấu thông báo là đã đọc
    @PutMapping("/mark-read/{id}")
    public Notification markAsRead(@PathVariable int id) {
        return notificationService.markAsRead(id);
    }

    // API xóa thông báo theo ID
    @DeleteMapping("/{id}")
    public boolean deleteNotification(@PathVariable int id) {
        return notificationService.deleteNotification(id);
    }
}