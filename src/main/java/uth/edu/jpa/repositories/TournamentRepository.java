package uth.edu.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uth.edu.jpa.models.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import uth.edu.jpa.models.Court;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uth.edu.jpa.models.NotificationModels;
import java.util.List;
import java.util.Optional;
import java.util.List;

@Repository

public interface TournamentRepository extends JpaRepository<Tournament, Long> {
    // Lấy các giải đấu sắp tới
    @Query("SELECT t FROM Tournament t WHERE t.startDate >= CURRENT_DATE ORDER BY t.startDate ASC")
    List<Tournament> findUpcomingTournaments();
}