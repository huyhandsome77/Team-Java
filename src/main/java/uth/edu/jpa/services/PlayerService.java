package uth.edu.jpa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uth.edu.jpa.models.Player;
import uth.edu.jpa.models.User;
import uth.edu.jpa.repositories.PlayerRepository;
import uth.edu.jpa.repositories.UserRepository;

import java.util.List;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private UserRepository userRepository; // Thêm UserRepository để truy cập bảng users

    public Player getPlayerByUser(User user) {
        return playerRepository.findByUser(user);
    }

    // Thêm phương thức đồng bộ
    public void syncUsersToPlayers() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (playerRepository.findByUser(user) == null) {
                Player player = new Player();
                player.setUser(user);
                player.setName(user.getEmail());
                playerRepository.save(player);
            }
        }
    }
}