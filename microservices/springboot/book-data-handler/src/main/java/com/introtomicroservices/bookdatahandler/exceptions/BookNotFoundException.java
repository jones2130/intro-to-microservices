package com.introtomicroservices.bookdatahandler.exceptions;

public class BookNotFoundException extends Exception {
    public BookNotFoundException() {
        super("This book was not found.");
    }
}
