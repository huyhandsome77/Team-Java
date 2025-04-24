package uth.edu.jpa.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uth.edu.jpa.models.Court;
import uth.edu.jpa.models.Player;
import uth.edu.jpa.models.Tournament;
import uth.edu.jpa.repositories.CourtRepository;
import uth.edu.jpa.repositories.PlayerRepository;
import uth.edu.jpa.repositories.TournamentRepository;
import java.util.Optional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class TournamentService {

    private static final Logger logger = LoggerFactory.getLogger(TournamentService.class);

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private CourtRepository courtRepository;

    @Autowired
    private PlayerRepository playerRepository;

    public Optional<Tournament> findById(Long id) {
        return tournamentRepository.findById(id);
    }

    public List<Tournament> getUpcomingTournaments() {
        return tournamentRepository.findUpcomingTournaments();
    }

    public Tournament createTournament(Tournament tournament, List<Long> courtIds, List<Long> playerIds) {
        logger.info("Creating tournament with courtIds: {}, playerIds: {}", courtIds, playerIds);
        List<Court> courts = courtRepository.findAllById(courtIds);
        List<Player> players = playerIds != null ? playerRepository.findAllById(playerIds) : new ArrayList<>();

        if (playerIds != null && !playerIds.isEmpty()) {
            List<Long> foundPlayerIds = players.stream().map(Player::getId).toList();
            List<Long> notFoundPlayerIds = playerIds.stream()
                    .filter(id -> !foundPlayerIds.contains(id))
                    .toList();
            if (!notFoundPlayerIds.isEmpty()) {
                throw new IllegalArgumentException("Các người chơi với ID " + notFoundPlayerIds + " không tồn tại!");
            }
        }

        tournament.setCourts(new HashSet<>(courts));
        tournament.setParticipants(new HashSet<>(players));

        return tournamentRepository.save(tournament);
    }

    public void deleteTournament(Long id) {
        logger.info("Deleting tournament with id: {}", id);
        tournamentRepository.deleteById(id);
    }



}