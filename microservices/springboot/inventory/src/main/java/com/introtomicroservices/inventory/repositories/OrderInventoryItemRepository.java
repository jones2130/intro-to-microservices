package com.introtomicroservices.inventory.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.introtomicroservices.inventory.models.OrderInventoryItem;
import com.introtomicroservices.inventory.models.keys.OrderInventoryItemKey;

@Repository
public interface OrderInventoryItemRepository extends JpaRepository<OrderInventoryItem, OrderInventoryItemKey>{
    @Query("select oii from OrderInventoryItem oii where oii.key.order.orderId = :orderId")
    List<OrderInventoryItem> findByOrderId(long orderId);
}
