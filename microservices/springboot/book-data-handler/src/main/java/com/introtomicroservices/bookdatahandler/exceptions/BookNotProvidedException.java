package com.introtomicroservices.bookdatahandler.exceptions;

public class BookNotProvidedException extends Exception {  
    public BookNotProvidedException() {
        super("No book was provided.");
    }
    
}
