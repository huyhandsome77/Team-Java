package uth.edu.jpa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uth.edu.jpa.models.Court;
import uth.edu.jpa.repositories.CourtRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CourtService {

    @Autowired
    private CourtRepository courtRepository;

    /**
     * Trả về toàn bộ danh sách sân.
     */
    public List<Court> getAllCourts() {
        return courtRepository.findAll();
    }

    /**
     * Tìm sân theo ID.
     */
    public Court getCourtById(Long id) {
        return courtRepository.findById(id).orElse(null);
    }

    /**
     * Tạo mới hoặc cập nhật sân.
     */
    public Court saveCourt(Court court) {
        return courtRepository.save(court);
    }

    /**
     * Xoá sân theo ID.
     */
    public boolean deleteCourt(Long id) {
        if (courtRepository.existsById(id)) {
            courtRepository.deleteById(id);
            return true;
        }
        return false;
    }
}