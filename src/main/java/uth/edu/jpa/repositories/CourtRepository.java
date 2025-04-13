package uth.edu.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uth.edu.jpa.models.Court;

public interface CourtRepository extends JpaRepository<Court, Long> {
}