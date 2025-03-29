package uth.edu.jpa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uth.edu.jpa.models.NotificationModels;
import uth.edu.jpa.services.NotificationServices;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationServices notificationService;

    @Autowired
    public NotificationController(NotificationServices notificationService) {
        this.notificationService = notificationService;
    }

    // API lấy thông báo theo ID
    @GetMapping("/{id}")
    public Optional<NotificationModels> getNotificationById(@PathVariable int id) {
        return notificationService.getByNotificationId(id);
    }

    // API lấy danh sách thông báo chưa đọc
    @GetMapping("/unread")
    public List<NotificationModels> getUnreadNotifications() {
        return notificationService.getUnreadNotifications();
    }

    // API lấy danh sách thông báo đã đọc
    @GetMapping("/read")
    public List<NotificationModels> getReadNotifications() {
        return notificationService.getReadNotifications();
    }


    // API tạo mới một thông báo
    @PostMapping("/create")
    public NotificationModels createNotification(@RequestParam String message) {
        return notificationService.createNotification(message);
    }

    // API đánh dấu thông báo là đã đọc
    @PutMapping("/mark-read/{id}")
    public NotificationModels markAsRead(@PathVariable int id) {
        return notificationService.markAsRead(id);
    }

    // API xóa thông báo theo ID
    @DeleteMapping("/{id}")
    public boolean deleteNotification(@PathVariable int id) {
        return notificationService.deleteNotification(id);
    }
}