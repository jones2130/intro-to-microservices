package com.introtomicroservices.inventory.models.keys;

import java.io.Serializable;

import com.introtomicroservices.inventory.models.InventoryItem;
import com.introtomicroservices.inventory.models.Order;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class OrderInventoryItemKey implements Serializable {
    @ManyToOne
    @JoinColumn(name="bookorderid", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name="itemid", nullable = false)
    private InventoryItem inventoryItem;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public InventoryItem getInventoryItem() {
        return inventoryItem;
    }

    public void setInventoryItem(InventoryItem inventoryItem) {
        this.inventoryItem = inventoryItem;
    }
}
