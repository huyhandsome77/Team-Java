package uth.edu.jpa.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uth.edu.jpa.models.Court;
import uth.edu.jpa.models.Player;
import uth.edu.jpa.models.User;
import uth.edu.jpa.repositories.CourtRepository;
import uth.edu.jpa.repositories.PlayerRepository;
import uth.edu.jpa.repositories.UserRepository;

@Component
public class DatsanConfig {

    @Autowired
    private CourtRepository courtRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @PostConstruct
    public void init() {
        // Thêm court nếu chưa có
        if (courtRepository.count() == 0) {
            Court court1 = new Court();
            court1.setName("Sân 1");
            court1.setAvailable(true);
            courtRepository.save(court1);

            Court court2 = new Court();
            court2.setName("Sân 2");
            court2.setAvailable(true);
            courtRepository.save(court2);

            Court court3 = new Court();
            court3.setName("Sân 3");
            court3.setAvailable(true);
            courtRepository.save(court3);

            System.out.println("✅ Đã thêm 3 sân chơi: Sân 1, Sân 2, Sân 3");
        }




    }
}