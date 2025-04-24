package uth.edu.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uth.edu.jpa.models.Player;

import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    boolean existsByUserUserID(int userId);
}