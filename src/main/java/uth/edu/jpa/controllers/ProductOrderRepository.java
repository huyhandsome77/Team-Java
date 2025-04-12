package uth.edu.jpa.controllers;
import org.springframework.data.jpa.repository.JpaRepository;
import uth.edu.jpa.models.ProductOrder;
import uth.edu.jpa.models.*;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {

}
