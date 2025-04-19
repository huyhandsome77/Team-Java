package uth.edu.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uth.edu.jpa.models.Tournament;

import java.util.List;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {

    // Giải đấu sắp diễn ra
    @Query("SELECT t FROM Tournament t WHERE t.startDate >= CURRENT_DATE ORDER BY t.startDate ASC")
    List<Tournament> findUpcomingTournaments();

}
