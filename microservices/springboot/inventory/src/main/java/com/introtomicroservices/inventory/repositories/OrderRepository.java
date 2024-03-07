package com.introtomicroservices.inventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.introtomicroservices.shared.models.postgres.Order;

/**
 * @author jjones
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
    
}
