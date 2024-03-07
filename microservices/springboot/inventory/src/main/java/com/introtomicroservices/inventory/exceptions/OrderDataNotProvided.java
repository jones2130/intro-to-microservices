package com.introtomicroservices.inventory.exceptions;

/**
 * @author jjones
 */
public class OrderDataNotProvided extends Exception {
    public OrderDataNotProvided() {
        super("Order form data not provided.");
    }
}
