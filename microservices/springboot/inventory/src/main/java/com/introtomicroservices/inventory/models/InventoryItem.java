package com.introtomicroservices.inventory.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name="inventoryitem")
public class InventoryItem {
    @Id
    @SequenceGenerator(
        name="inventoryitem_itemid_seq",
        sequenceName="inventoryitem_itemid_seq",
        allocationSize=1)
    @GeneratedValue(
        generator = "inventoryitem_itemid_seq", 
        strategy = GenerationType.AUTO)
    @Column(name="itemid", nullable = false)
    private long itemId;

    @Column(name="bookid")
    private String bookId;

    @Column(name="quantity")
    private int quantity;

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
