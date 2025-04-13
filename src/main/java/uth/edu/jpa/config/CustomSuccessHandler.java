package uth.edu.jpa.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        for (GrantedAuthority auth : authentication.getAuthorities()) {
            String role = auth.getAuthority();

            switch (role) {
                case "ROLE_ADMIN":
                    response.sendRedirect("/admin/dashboard");
                    return;
                case "ROLE_COURT_MANAGER":
                    response.sendRedirect("/QuanLySan");
                    return;
                case "ROLE_COACH":
                    response.sendRedirect("/coach/home");
                    return;
                case "ROLE_PLAYER":
                    response.sendRedirect("/player/home");
                    return;
                default:
                    break;
            }
        }
        // Mặc định nếu không có role
        response.sendRedirect("/home");
    }
}