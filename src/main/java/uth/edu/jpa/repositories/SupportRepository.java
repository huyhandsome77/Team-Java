package uth.edu.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uth.edu.jpa.models.SupportRequest;

import java.util.List;

@Repository
public interface SupportRepository extends JpaRepository<SupportRequest, Long> {
    List<SupportRequest> findAllByOrderByCreatedAtDesc();
}
