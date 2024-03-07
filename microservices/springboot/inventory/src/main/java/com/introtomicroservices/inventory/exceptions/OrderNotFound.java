package com.introtomicroservices.inventory.exceptions;

public class OrderNotFound extends Exception {
    public OrderNotFound() {
        super("This order was not found.");
    }
}
