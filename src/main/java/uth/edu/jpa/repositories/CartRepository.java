package uth.edu.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uth.edu.jpa.models.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {}