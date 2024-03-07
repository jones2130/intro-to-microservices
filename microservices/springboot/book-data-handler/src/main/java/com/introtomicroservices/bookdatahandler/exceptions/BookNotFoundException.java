package com.introtomicroservices.bookdatahandler.exceptions;

/**
 * @author jjones
 */
public class BookNotFoundException extends Exception {
    public BookNotFoundException() {
        super("This book was not found.");
    }
}
