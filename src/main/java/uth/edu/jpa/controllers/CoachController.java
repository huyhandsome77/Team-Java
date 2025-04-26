package uth.edu.jpa.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uth.edu.jpa.models.Player;
import uth.edu.jpa.repositories.CourtRepository;
import uth.edu.jpa.repositories.TournamentRepository;
import uth.edu.jpa.services.*;
import uth.edu.jpa.repositories.PlayerRepository;
import uth.edu.jpa.models.Tournament;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uth.edu.jpa.models.*;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/coach")


public class CoachController {
    private static final Logger logger = LoggerFactory.getLogger(CoachController.class);

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private CourtRepository courtRepository;

    @Autowired
    private TournamentService tournamentService;

    @Autowired
    private CourtService courtService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private PlayerService playerService;
    @Autowired
    private UserService userService;
    @Autowired
    private HighlightedStudentService highlightedStudentService;


    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("coachName", "Huấn Luyện Viên");
        model.addAttribute("topHighlightedStudents", highlightedStudentService.getTopHighlightedStudents());

        return "coach/Coach_Dashboard";
    }

    @ModelAttribute("courts")
    public List<Court> getAllCourts() {
        return courtService.getAllCourts();
    }

    @GetMapping("/datsan")
    public String showBookingForm(Model model) {
        // Thêm đối tượng booking vào model để form có thể binding với đối tượng này
        model.addAttribute("booking", new Booking());

        // Lấy danh sách các bookings đã có
        model.addAttribute("bookings", bookingService.getAllBookings());

        // Lấy danh sách các sân từ courtService
        List<Court> courts = courtService.getAllCourts();  // Sửa lại phương thức ở đây
        model.addAttribute("courts", courts);

        return "coach/Coach_DatSan";  // Trả về view tương ứng
    }

    @PostMapping("/datsan")
    public String createBooking(@ModelAttribute("booking") Booking booking,
                                @RequestParam("court") Long courtId, // lấy id từ form
                                RedirectAttributes redirectAttributes) {

        Court court = courtRepository.findById(courtId).orElse(null);
        if (court == null) {
            redirectAttributes.addFlashAttribute("message", "Không tìm thấy sân!");
            return "redirect:/coach/datsan";
        }

        booking.setCourt(court); // set lại sân vào booking
        bookingService.saveBooking(booking);
        redirectAttributes.addFlashAttribute("message", "Đặt sân thành công!");
        return "redirect:/coach/datsan";
    }



    @PostMapping("/deleteBooking/{id}")
    public String deleteBooking(@PathVariable("id") Long id,
                                RedirectAttributes redirectAttributes) {
        bookingService.deleteBookingById(id);
        redirectAttributes.addFlashAttribute("message", "Xóa đặt sân thành công!");
        return "redirect:/coach/datsan";
    }


    @GetMapping("/players")
    public String players(Model model) {
        model.addAttribute("topHighlightedStudents", highlightedStudentService.getTopHighlightedStudents());
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

    // Trang chỉnh sửa người chơi
    @GetMapping("/players/edit/{id}")
    public String editPlayerForm(@PathVariable Long id, Model model) {
        Optional<Player> playerOptional = playerService.findById(id);
        if (playerOptional.isPresent()) {
            model.addAttribute("player", playerOptional.get());
            return "coach/Coach_Players"; // Quay lại trang người chơi với form chỉnh sửa
        } else {
            model.addAttribute("message", "Không tìm thấy người chơi.");
            return "redirect:/coach/players"; // Quay lại danh sách người chơi
        }
    }

    @PostMapping("/players/update/{id}")
    public String updatePlayer(@PathVariable Long id,
                               @RequestParam String name,
                               @RequestParam String email,
                               @RequestParam(required = false) String phone,
                               RedirectAttributes redirectAttributes) {
        Player player = playerRepository.findById(id).orElse(null);
        if (player != null) {
            player.setName(name);
            player.setEmail(email);
            player.setPhone(phone);
            playerRepository.save(player);
            redirectAttributes.addFlashAttribute("message", "Cập nhật thành công.");
        }
        return "redirect:/coach/players";
    }



    @PostMapping("/players/delete/{id}")
    @Transactional
    public String deletePlayer(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            playerRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Xóa người chơi thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Lỗi khi xóa người chơi: " + e.getMessage());
        }
        return "redirect:/coach/players";
    }





    @GetMapping("/tournament")
    @Transactional
    public String tournaments(Model model) {
        logger.info("Starting /coach/tournament");
        playerService.syncUsersToPlayers();
        logger.info("Finished syncing users to players");

        List<Tournament> tournaments = tournamentService.getUpcomingTournaments();
        List<Player> players = playerRepository.findAll();
        logger.info("Found {} players: {}", players.size(), players);

        model.addAttribute("tournaments", tournaments);
        model.addAttribute("players", players);
        model.addAttribute("topHighlightedStudents", highlightedStudentService.getTopHighlightedStudents());

        if (players.isEmpty()) {
            logger.warn("No players found in the system");
            model.addAttribute("message", "Không có người chơi nào trong hệ thống.");
        }

        model.addAttribute("tournament", new Tournament());
        return "coach/Coach_Tournament";
    }

    @PostMapping("/tournament")
    public String createTournament(@ModelAttribute("tournament") Tournament tournament,
                                   @RequestParam(value = "courtIds", required = false) List<Long> courtIds,
                                   @RequestParam(value = "playerIds", required = false) List<Long> playerIds,
                                   RedirectAttributes redirectAttributes) {
        logger.info("Creating tournament with courtIds: {}, playerIds: {}", courtIds, playerIds);
        try {
            if (courtIds == null || courtIds.isEmpty()) {
                redirectAttributes.addFlashAttribute("message", "Bạn phải chọn ít nhất một sân!");
                return "redirect:/coach/tournament";
            }
            if (playerIds == null || playerIds.isEmpty()) {
                redirectAttributes.addFlashAttribute("message", "Bạn phải chọn ít nhất một người chơi!");
                return "redirect:/coach/tournament";
            }

            tournamentService.createTournament(tournament, courtIds, playerIds);
            redirectAttributes.addFlashAttribute("message", "Giải đấu đã được tạo thành công!");
        } catch (Exception e) {
            logger.error("Error creating tournament: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("message", "Lỗi khi tạo giải đấu: " + e.getMessage());
        }
        return "redirect:/coach/tournament";
    }

    @GetMapping("/tournament/delete")
    public String deleteTournament(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        logger.info("Deleting tournament with id: {}", id);
        try {
            tournamentService.deleteTournament(id);
            redirectAttributes.addFlashAttribute("message", "Xóa giải đấu thành công!");
        } catch (Exception e) {
            logger.error("Error deleting tournament: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("message", "Lỗi khi xóa giải đấu: " + e.getMessage());
        }
        return "redirect:/coach/tournament";
    }




    @GetMapping("/highlighted")
    public String highlightedStudents(Model model) {
        List<HighlightedStudent> highlightedStudents = highlightedStudentService.findAll();
        List<Player> players = playerService.getAllPlayers(); // lấy danh sách tất cả Player
        List<HighlightedStudent> allStudents = highlightedStudentService.findAll();

        // Sắp xếp giảm dần theo progress
        allStudents.sort((a, b) -> Integer.compare(b.getProgress(), a.getProgress()));

        // Nếu bạn muốn lấy ví dụ Top 3 người, thì:
        List<HighlightedStudent> topStudents = allStudents.stream().limit(3).toList();

        model.addAttribute("highlightedStudents", allStudents);
        model.addAttribute("topHighlightedStudents", topStudents);

        model.addAttribute("highlightedStudents", highlightedStudents);
        model.addAttribute("players", players);
        model.addAttribute("highlightedStudent", new HighlightedStudent()); // object cho form tạo mới

        return "coach/Coach_HighlightedStudents"; // Trang giao diện bạn đã chuẩn bị
    }

    @PostMapping("/highlighted/add")
    public String addHighlightedStudent(@ModelAttribute("highlightedStudent") HighlightedStudent highlightedStudent,
                                        RedirectAttributes redirectAttributes) {
        highlightedStudentService.save(highlightedStudent);
        redirectAttributes.addFlashAttribute("message", "Đánh giá học viên thành công!");
        return "redirect:/coach/highlighted";
    }

    @PostMapping("/highlighted/delete/{id}")
    public String deleteHighlightedStudent(@PathVariable Integer id,
                                           RedirectAttributes redirectAttributes) {
        highlightedStudentService.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Xóa học viên nổi bật thành công!");
        return "redirect:/coach/highlighted";
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
