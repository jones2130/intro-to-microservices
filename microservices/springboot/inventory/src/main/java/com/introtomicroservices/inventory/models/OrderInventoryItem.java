package com.introtomicroservices.inventory.models;

import com.introtomicroservices.inventory.models.keys.OrderInventoryItemKey;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="bookorderinventoryitem")
public class OrderInventoryItem {
    @EmbeddedId
    private OrderInventoryItemKey key;

    public OrderInventoryItemKey getKey() {
        return key;
    }

    public void setKey(OrderInventoryItemKey key) {
        this.key = key;
    }
}
