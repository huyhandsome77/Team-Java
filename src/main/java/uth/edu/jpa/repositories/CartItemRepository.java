package uth.edu.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uth.edu.jpa.models.CartItemEntity;

public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {}