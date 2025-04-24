package uth.edu.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uth.edu.jpa.models.DeliveryAddress;

@Repository
public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Long> {
}