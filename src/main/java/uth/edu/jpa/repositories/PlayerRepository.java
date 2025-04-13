package uth.edu.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uth.edu.jpa.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import uth.edu.jpa.models.Court;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uth.edu.jpa.models.NotificationModels;
import java.util.List;
import java.util.Optional;

@Repository

public interface PlayerRepository extends JpaRepository<Player, Long> {
    // Tìm kiếm người chơi theo tên
    List<Player> findByNameContaining(String name);
}