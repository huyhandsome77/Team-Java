package uth.edu.jpa.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uth.edu.jpa.models.*;
import uth.edu.jpa.services.*;

@Controller
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<String> addUser (@RequestBody User user) {
        userService.addUser (user);
        return ResponseEntity.ok("Người dùng đã được thêm thành công!");
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser (@PathVariable int id) {
        userService.deleteUser (id);
        return ResponseEntity.ok("Người dùng đã được xóa thành công!");
    }
    @PutMapping("/update")
    public ResponseEntity<User> updateUser (@RequestBody User user) {
        User updatedUser  = userService.updateUser (user);
        return ResponseEntity.ok(updatedUser );
    }
}