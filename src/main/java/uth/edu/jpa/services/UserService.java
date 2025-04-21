package uth.edu.jpa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uth.edu.jpa.models.User;
import uth.edu.jpa.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void addUser (User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }
    public void deleteUser (int id) {
        userRepository.deleteById(id);
    }
    public User updateUser (User user) {
        // Kiểm tra xem người dùng có nhập mật khẩu mới hay không
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            // Mã hóa mật khẩu mới
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
        } else {
            // Nếu không có mật khẩu mới, giữ nguyên mật khẩu cũ
            User existingUser  = userRepository.findById(user.getUserID()).orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));
            user.setPassword(existingUser .getPassword());
        }

        return userRepository.save(user);
    }
}