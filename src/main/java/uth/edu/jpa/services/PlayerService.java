package uth.edu.jpa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uth.edu.jpa.models.Player;
import uth.edu.jpa.models.User;
import uth.edu.jpa.repositories.PlayerRepository;
import uth.edu.jpa.repositories.UserRepository;
import uth.edu.jpa.models.User.Role;
import java.util.Optional;
import java.util.List;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private UserRepository userRepository;

    public void syncUsersToPlayers() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            boolean exists = playerRepository.existsByUserUserID(user.getUserID());
            if (user.getRole() == Role.PLAYER && !exists) {
                Player player = new Player();
                player.setName(user.getFullname());
                player.setEmail(user.getEmail());
                player.setPhone("Chưa có số");
                player.setUser(user);
                playerRepository.save(player);
            }
        }
    }

    public Optional<Player> findById(Long id) {
        return playerRepository.findById(id);
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }


    public void updatePlayer(Player player) throws Exception {
        Optional<Player> existingPlayerOpt = playerRepository.findById(player.getId());
        if (existingPlayerOpt.isPresent()) {
            Player existingPlayer = existingPlayerOpt.get();
            existingPlayer.setName(player.getName());
            existingPlayer.setEmail(player.getEmail());
            existingPlayer.setPhone(player.getPhone());
            existingPlayer.setUser(player.getUser());
            playerRepository.save(existingPlayer);
        } else {
            throw new Exception("Người chơi không tồn tại");
        }
    }
}