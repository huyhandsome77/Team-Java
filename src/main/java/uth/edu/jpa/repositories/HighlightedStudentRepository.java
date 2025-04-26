package uth.edu.jpa.repositories;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.jpa.repository.JpaRepository;
import uth.edu.jpa.models.HighlightedStudent;
import java.util.List;

public interface HighlightedStudentRepository extends JpaRepository<HighlightedStudent, Integer> {
    @Query("SELECT h FROM HighlightedStudent h ORDER BY h.progress DESC")
    List<HighlightedStudent> findTopStudents();
}