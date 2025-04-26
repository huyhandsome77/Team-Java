package uth.edu.jpa.services;

import uth.edu.jpa.models.HighlightedStudent;
import uth.edu.jpa.repositories.HighlightedStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class HighlightedStudentService {

    @Autowired
    private HighlightedStudentRepository highlightedStudentRepository;

    public List<HighlightedStudent> findAll() {
        return highlightedStudentRepository.findAll();
    }



    public void save(HighlightedStudent highlightedStudent) {
        highlightedStudentRepository.save(highlightedStudent);
    }

    public void deleteById(Integer id) {
        highlightedStudentRepository.deleteById(id);
    }

    public Optional<HighlightedStudent> findById(Integer id) {
        return highlightedStudentRepository.findById(id);
    }
    public List<HighlightedStudent> getTopHighlightedStudents() {
        return highlightedStudentRepository.findTopStudents();
    }


}