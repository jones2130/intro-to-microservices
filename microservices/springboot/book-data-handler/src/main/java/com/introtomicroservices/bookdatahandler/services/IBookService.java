package com.introtomicroservices.bookdatahandler.services;

import com.introtomicroservices.bookdatahandler.exceptions.BookNotFoundException;
import com.introtomicroservices.bookdatahandler.exceptions.BookNotProvidedException;
import com.introtomicroservices.bookdatahandler.forms.BookForm;
import com.introtomicroservices.shared.models.mongodb.Book;

/**
 * @author jjones
 */
public interface IBookService {
    public Book getById(String id) throws BookNotFoundException;
    public Book create(BookForm book) throws BookNotProvidedException;
    public Book update(String id, BookForm book) throws BookNotFoundException, BookNotProvidedException;
    public void delete(String id) throws BookNotFoundException;
}