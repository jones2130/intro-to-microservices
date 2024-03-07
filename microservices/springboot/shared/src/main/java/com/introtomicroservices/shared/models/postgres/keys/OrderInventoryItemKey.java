package com.introtomicroservices.shared.models.postgres.keys;

import java.io.Serializable;

import com.introtomicroservices.shared.models.postgres.InventoryItem;
import com.introtomicroservices.shared.models.postgres.Order;

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

    public com.introtomicroservices.shared.models.postgres.Order getOrder() {
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
