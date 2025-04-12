package uth.edu.jpa.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uth.edu.jpa.models.Court;
import uth.edu.jpa.repositories.CourtRepository;

@Component
public class DataInitializer {

    @Autowired
    private CourtRepository courtRepository;

    @PostConstruct
    public void init() {
        if (courtRepository.count() == 0) {
            Court court1 = new Court();
            court1.setName("Sân 1");
            court1.setAvailable(true); // Gán giá trị cho available
            courtRepository.save(court1);

            Court court2 = new Court();
            court2.setName("Sân 2");
            court2.setAvailable(true); // Gán giá trị cho available
            courtRepository.save(court2);

            Court court3 = new Court();
            court3.setName("Sân 3");
            court3.setAvailable(true); // Gán giá trị cho available
            courtRepository.save(court3);

            System.out.println("Đã thêm 3 sân chơi: Sân 1, Sân 2, Sân 3");
        }
    }
}