package uth.edu.jpa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uth.edu.jpa.models.Booking;
import uth.edu.jpa.models.Court;
import uth.edu.jpa.models.Player;
import uth.edu.jpa.repositories.TournamentRepository;
import uth.edu.jpa.services.*;
import uth.edu.jpa.repositories.PlayerRepository;
import uth.edu.jpa.models.Tournament;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uth.edu.jpa.models.*;
import java.util.List;


@Controller
@RequestMapping("/coach")
public class CoachController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TournamentService tournamentService;

    @Autowired
    private PlayerService playerService;
    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("coachName", "Huấn Luyện Viên");
        return "coach/Coach_Dashboard";
    }

    @GetMapping("/datsan")
    public String datSan(Model model) {
        model.addAttribute("coachName", "Huấn Luyện Viên");
        model.addAttribute("booking", new Booking());
        List<Court> courts = bookingService.getAllCourts();
        model.addAttribute("courts", courts);
        List<Booking> bookings = bookingService.getAllBookings();
        model.addAttribute("bookings", bookings);
        return "coach/Coach_DatSan";
    }

    @GetMapping("/players")
    public String players(Model model) {
        try {
            // Đồng bộ dữ liệu từ users sang players
            playerService.syncUsersToPlayers();

            // Lấy danh sách Player
            List<Player> players = playerRepository.findAll();
            if (players.isEmpty()) {
                model.addAttribute("message", "Không có người chơi nào trong hệ thống.");
            } else {
                model.addAttribute("players", players);
            }
        } catch (Exception e) {
            model.addAttribute("message", "Lỗi khi lấy danh sách người chơi: " + e.getMessage());
        }
        return "coach/Coach_Players";
    }

    // Thêm vào CoachController.java
    @GetMapping("/players/edit/{id}")
    public String editPlayer(@PathVariable("id") Long id, Model model) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player không tồn tại"));
        model.addAttribute("player", player);
        return "coach/Coach_EditPlayer"; // Tạo template mới để chỉnh sửa
    }

    @PostMapping("/players/delete/{id}")
    public String deletePlayer(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            playerRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Xóa người chơi thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Lỗi khi xóa người chơi: " + e.getMessage());
        }
        return "redirect:/coach/players";
    }

    @PostMapping("/players/edit/{id}")
    public String updatePlayer(@PathVariable("id") Long id, @ModelAttribute("player") Player player, RedirectAttributes redirectAttributes) {
        try {
            Player existingPlayer = playerRepository.findById(id).orElse(null);
            if (existingPlayer == null) {
                redirectAttributes.addFlashAttribute("message", "Không tìm thấy người chơi với ID: " + id);
                return "redirect:/coach/players";
            }

            if (player.getName() == null || player.getName().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("message", "Tên người chơi không được để trống!");
                return "redirect:/coach/players/edit/" + id;
            }

            existingPlayer.setName(player.getName());
            playerRepository.save(existingPlayer);
            redirectAttributes.addFlashAttribute("message", "Cập nhật người chơi thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Lỗi khi cập nhật người chơi: " + e.getMessage());
        }
        return "redirect:/coach/players";
    }

    @GetMapping("/tournament")
    public String tournaments(Model model) {
        List<Tournament> tournaments = tournamentService.getUpcomingTournaments();
        List<Court> courts = bookingService.getAllCourts();  // Lấy danh sách sân
        List<Player> players = playerRepository.findAll();   // Lấy danh sách người chơi

        model.addAttribute("coachName", "Huấn Luyện Viên");
        model.addAttribute("tournaments", tournaments);
        model.addAttribute("courts", courts);
        model.addAttribute("players", players);

        model.addAttribute("players", playerRepository.findAll());
        model.addAttribute("tournament", new Tournament());  // Đảm bảo thêm đối tượng tournament vào Model

        return "coach/Coach_Tournament";
    }

    @PostMapping("/tournament")
    public String createTournament(@ModelAttribute("tournament") Tournament tournament,
                                   @RequestParam(value = "courtIds", required = false) List<Long> courtIds,
                                   @RequestParam(value = "playerIds", required = false) List<Long> playerIds,
                                   RedirectAttributes redirectAttributes) {
        try {
            if (courtIds == null || courtIds.isEmpty()) {
                redirectAttributes.addFlashAttribute("message", "Bạn phải chọn ít nhất một sân!");
                return "redirect:/coach/tournament";
            }

            Player player = playerRepository.findById(1L)
                    .orElseThrow(() -> new RuntimeException("Player không tồn tại"));

            tournamentService.createTournament(tournament, courtIds, playerIds);
            redirectAttributes.addFlashAttribute("message", "Giải đấu đã được tạo thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Lỗi khi tạo giải đấu: " + e.getMessage());
        }

        return "redirect:/coach/tournament";
    }

    @GetMapping("/tournament/delete/{id}")
    public String deleteTournament(@PathVariable("id") Long id, Model model) {
        tournamentService.deleteTournament(id);
        model.addAttribute("message", "Giải đấu đã được xóa!");
        return "redirect:/coach/tournament";
    }

    @GetMapping("/chat")
    public String chats(Model model) {
        model.addAttribute("coachName", "Huấn Luyện Viên");
        return "coach/Coach_Chat";
    }

    // Đây là phương thức giả sử để lấy thông tin User đã đăng nhập
    private User getLoggedInUser() {
        // Cần thay thế logic này với cách thức lấy User thực tế từ session hoặc security context
        return new User(); // Ví dụ
    }
}
