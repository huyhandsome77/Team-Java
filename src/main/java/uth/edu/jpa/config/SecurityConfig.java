package uth.edu.jpa.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.core.Authentication;
import uth.edu.jpa.services.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserService userService;

    // Sử dụng @Lazy để trì hoãn khởi tạo UserService
    public SecurityConfig(@Lazy UserService userService) {
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Tắt CSRF cho dễ dàng khi phát triển
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/do-login", "/register", "/css/**", "/js/**").permitAll()  // Cho phép truy cập không cần xác thực
                        .requestMatchers("/admin/**").hasRole("ADMIN")  // Chỉ cho phép ADMIN truy cập
                        .requestMatchers("/coach/**").hasRole("COACH")  // Chỉ cho phép COACH truy cập
                        .requestMatchers("/player/**").hasRole("PLAYER")  // Chỉ cho phép PLAYER truy cập
                        .anyRequest().authenticated()  // Các request khác cần phải xác thực
                )
                .formLogin(form -> form
                        .loginPage("/login")  // Cấu hình trang đăng nhập tùy chỉnh
                        .loginProcessingUrl("/do-login")  // URL xử lý đăng nhập
                        .successHandler(customSuccessHandler())  // Xử lý sau khi đăng nhập thành công
                        .permitAll()  // Cho phép tất cả người dùng truy cập trang đăng nhập
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")  // URL xử lý đăng xuất
                        .logoutSuccessUrl("/login?logout")  // Chuyển hướng về trang đăng nhập sau khi đăng xuất
                        .permitAll()  // Cho phép tất cả người dùng đăng xuất
                )
                .build();
    }

    // Xử lý chuyển hướng sau khi đăng nhập thành công theo vai trò người dùng
    @Bean
    public AuthenticationSuccessHandler customSuccessHandler() {
        return (HttpServletRequest request, HttpServletResponse response, Authentication authentication) -> {
            String role = authentication.getAuthorities().iterator().next().getAuthority();

            switch (role) {
                case "ROLE_ADMIN" -> response.sendRedirect("/admin/dashboard");
                case "ROLE_COACH" -> response.sendRedirect("/coach/home");
                case "ROLE_PLAYER" -> response.sendRedirect("/player/home");
                default -> response.sendRedirect("/");
            }
        };
    }

    // Đưa Bean PasswordEncoder vào Container Spring
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Mã hóa mật khẩu với BCrypt
    }
}
