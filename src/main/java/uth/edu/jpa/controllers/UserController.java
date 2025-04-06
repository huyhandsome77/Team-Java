package uth.edu.jpa.controllers;

import org.springframework.web.bind.annotation.*;
import uth.edu.jpa.models.User;
import uth.edu.jpa.models.User.Role;
import uth.edu.jpa.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // API đăng ký user
    @PostMapping("/register")
    public User registerUser(@RequestParam String username,
                             @RequestParam String password,
                             @RequestParam String email,
                             @RequestParam String fullname,
                             @RequestParam Role role) {
        return userService.registerUser(username, password, email, fullname, role);
    }

    // API lấy infor theo ID
    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    // API lấy infor theo username
    @GetMapping("/username/{username}")
    public Optional<User> getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }
}
