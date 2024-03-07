package com.introtomicroservices.bookdatahandler.exceptions;

/**
 * @author jjones
 */
public class BookNotProvidedException extends Exception {  
    public BookNotProvidedException() {
        super("No book was provided.");
    }
    
}
