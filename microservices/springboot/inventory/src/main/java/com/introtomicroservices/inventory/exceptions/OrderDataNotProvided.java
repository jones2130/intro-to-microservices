package com.introtomicroservices.inventory.exceptions;

public class OrderDataNotProvided extends Exception {
    public OrderDataNotProvided() {
        super("Order form data not provided.");
    }
}
