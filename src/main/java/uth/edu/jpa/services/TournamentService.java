package uth.edu.jpa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uth.edu.jpa.models.Court;
import uth.edu.jpa.models.Player;
import uth.edu.jpa.models.Tournament;
import uth.edu.jpa.repositories.CourtRepository;
import uth.edu.jpa.repositories.PlayerRepository;
import uth.edu.jpa.repositories.TournamentRepository;

import java.util.List;

@Service
public class TournamentService {

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private CourtRepository courtRepository;  // Để truy vấn sân

    @Autowired
    private PlayerRepository playerRepository;  // Để truy vấn người chơi

    // Phương thức lấy danh sách các giải đấu sắp tới
    public List<Tournament> getUpcomingTournaments() {
        return tournamentRepository.findUpcomingTournaments();
    }

    // Phương thức tạo giải đấu
    public Tournament createTournament(Tournament tournament, List<Long> courtIds, List<Long> playerIds) {
        // Lấy các sân từ danh sách courtIds
        List<Court> courts = courtRepository.findAllById(courtIds);
        // Lấy các người chơi từ danh sách playerIds
        List<Player> players = playerRepository.findAllById(playerIds);

        // Gán các sân và người chơi vào giải đấu

        // Lưu giải đấu và trả về
        return tournamentRepository.save(tournament);
    }

    // Phương thức xóa giải đấu
    public void deleteTournament(Long id) {
        tournamentRepository.deleteById(id);
    }

    // Các phương thức khác của TournamentService nếu có
}
