package com.introtomicroservices.bookdatahandler.services;

import com.introtomicroservices.bookdatahandler.exceptions.BookNotFoundException;
import com.introtomicroservices.bookdatahandler.exceptions.BookNotProvidedException;
import com.introtomicroservices.bookdatahandler.models.Book;

public interface IBookService {
    public Book getById(String id) throws BookNotFoundException;
    public Book create(Book book) throws BookNotProvidedException;
    public Book update(String id, Book book) throws BookNotFoundException, BookNotProvidedException;
    public void delete(String id) throws BookNotFoundException;
}