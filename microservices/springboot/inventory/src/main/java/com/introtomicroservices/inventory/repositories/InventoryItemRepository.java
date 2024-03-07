package com.introtomicroservices.inventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.introtomicroservices.shared.models.postgres.InventoryItem;

/**
 * @author jjones
 */
@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long>{
    
}
