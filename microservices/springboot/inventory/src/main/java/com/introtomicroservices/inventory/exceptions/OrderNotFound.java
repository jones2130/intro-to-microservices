package com.introtomicroservices.inventory.exceptions;

/**
 * @author jjones
 */
public class OrderNotFound extends Exception {
    public OrderNotFound() {
        super("This order was not found.");
    }
}
