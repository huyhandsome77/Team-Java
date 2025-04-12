package uth.edu.jpa.controllers;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uth.edu.jpa.models.User;
import uth.edu.jpa.repositories.UserRepository;

@Controller
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User()); // user là th:object
        return "login_register";
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute("user") User user) {
        // TODO: Mã hóa mật khẩu, gán role mặc định
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(User.Role.PLAYER);
        userRepository.save(user);
        return "redirect:/login?registered";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("user", new User());
        return "login_register";
    }
}
