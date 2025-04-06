package uth.edu.jpa.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uth.edu.jpa.models.User; // Đảm bảo sử dụng lớp User của ứng dụng, không phải của Spring Security
import uth.edu.jpa.models.User.Role;
import uth.edu.jpa.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder(); // Dùng BCrypt để mã hóa mật khẩu
    }

    // Đăng ký người dùng mới
    public User registerUser(String username, String rawPassword, String email, String fullname, Role role) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username đã tồn tại!");
        }
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email đã tồn tại!");
        }

        String encodedPassword = passwordEncoder.encode(rawPassword); // Mã hóa mật khẩu
        User newUser = new User(username, encodedPassword, email, fullname, role);
        return userRepository.save(newUser); // Lưu vào database
    }

    // Lấy thông tin người dùng theo ID
    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    // Lấy thông tin người dùng theo tên đăng nhập
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Kiểm tra mật khẩu (sử dụng BCryptPasswordEncoder)
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    // Xử lý load thông tin người dùng từ username (phục vụ cho Spring Security)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username không tồn tại!"));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRole().getAuthorities() // Chuyển Role thành Authorities để Spring Security có thể sử dụng
        );
    }
}
