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
import java.util.stream.Collectors;

@Service
public class TournamentService {

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private CourtRepository courtRepository;

    public List<Tournament> getAllTournaments() {
        return tournamentRepository.findAll();
    }

    public Tournament getTournamentById(Long id) {
        return tournamentRepository.findById(id).orElse(null);
    }

    public List<Tournament> getUpcomingTournaments() {
        return tournamentRepository.findUpcomingTournaments().stream().limit(3).collect(Collectors.toList());
    }

    public Tournament createTournament(Tournament tournament, List<Long> courtIds, List<Long> playerIds) {
        // Thiết lập các sân thi đấu
        if (courtIds != null && !courtIds.isEmpty()) {
            for (Long courtId : courtIds) {
                Court court = courtRepository.findById(courtId).orElse(null);
                if (court != null) {
                    tournament.addCourt(court);
                }
            }
        }

        // Thiết lập các người chơi tham gia
        if (playerIds != null && !playerIds.isEmpty()) {
            for (Long playerId : playerIds) {
                Player player = playerRepository.findById(playerId).orElse(null);
                if (player != null) {
                    tournament.addParticipant(player);
                }
            }
        }

        return tournamentRepository.save(tournament);
    }

    public Tournament updateTournament(Tournament tournament) {
        return tournamentRepository.save(tournament);
    }

    public void deleteTournament(Long id) {
        tournamentRepository.deleteById(id);
    }
}