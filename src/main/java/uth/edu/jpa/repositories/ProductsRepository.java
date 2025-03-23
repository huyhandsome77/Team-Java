package uth.edu.jpa.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uth.edu.jpa.models.Products;
import java.util.Optional;
@Repository
public interface ProductsRepository extends JpaRepository<Products, Long>{
    Optional<Products> findByProductID(Long productID);

}
