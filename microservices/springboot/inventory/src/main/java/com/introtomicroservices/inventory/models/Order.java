package com.introtomicroservices.inventory.models;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name="bookorder")
public class Order {
    @Id
    @SequenceGenerator(
        name="bookorder_bookorderid_seq",
        sequenceName="bookorder_bookorderid_seq",
        allocationSize=1)
    @GeneratedValue(
        generator = "bookorder_bookorderid_seq",
        strategy = GenerationType.AUTO)
    @Column(name="bookorderid", nullable = false)
    private long orderId;

    @Column(name="notes")
    private String notes;

    @Column(name="timestamp")
    private Date timestamp;

    @OneToMany
    @JoinColumn(name="bookorderid")
    @JsonIgnore
    List<OrderInventoryItem> inventoryItems;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public List<OrderInventoryItem> getInventoryItems() {
        return inventoryItems;
    }

    public void setInventoryItems(List<OrderInventoryItem> inventoryItems) {
        this.inventoryItems = inventoryItems;
    }
}
