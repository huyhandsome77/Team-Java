package uth.edu.jpa.services;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import uth.edu.jpa.models.User;
import uth.edu.jpa.models.User.Role;
import uth.edu.jpa.repositories.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // Khởi tạo UserService với PasswordEncoder riêng
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder; // Không khởi tạo ở đây nữa
    }
    public User register(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Lấy thông tin người dùng theo ID
    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    // Lấy thông tin người dùng theo tên đăng nhập
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Kiểm tra mật khẩu (dùng cho login)
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    // Spring Security sử dụng method này để lấy UserDetails từ username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username không tồn tại!"));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRole().getAuthorities() // Chuyển Role thành Authorities
        );
    }
}
