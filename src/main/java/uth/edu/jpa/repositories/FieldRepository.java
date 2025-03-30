package uth.edu.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uth.edu.jpa.models.Field;

import java.util.List;

@Repository
public interface FieldRepository extends JpaRepository<Field, Long> {

    @Transactional(readOnly = true)
    List<Field> findByNameContainingIgnoreCase(String name);

    @Transactional(readOnly = true)
    List<Field> findByAvailableTrue();

    @Transactional(readOnly = true)
    List<Field> findByLocationContainingIgnoreCase(String location);

    @Transactional(readOnly = true)
    List<Field> findByPricePerHourBetween(double minPrice, double maxPrice);
}