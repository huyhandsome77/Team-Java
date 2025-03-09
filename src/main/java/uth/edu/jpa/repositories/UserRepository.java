package uth.edu.jpa.repositories;
import org.springframework.stereotype.Repository;
import uth.edu.jpa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
@Repository

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
