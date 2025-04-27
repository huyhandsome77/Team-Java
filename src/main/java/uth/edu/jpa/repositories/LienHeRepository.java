package uth.edu.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uth.edu.jpa.models.LienHe;

import java.util.List;

public interface LienHeRepository extends JpaRepository<LienHe, Long> {
    List<LienHe> findByResolved(boolean resolved);
}