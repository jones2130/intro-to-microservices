package com.introtomicroservices.bookdatahandler.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.introtomicroservices.bookdatahandler.exceptions.BookNotFoundException;
import com.introtomicroservices.bookdatahandler.exceptions.BookNotProvidedException;
import com.introtomicroservices.bookdatahandler.forms.BookForm;
import com.introtomicroservices.bookdatahandler.services.IBookService;
import com.introtomicroservices.shared.models.ApiMessage;
import com.introtomicroservices.shared.models.mongodb.Book;

/**
 * @author jjones
 */
@RestController
@RequestMapping("/book/data")
public class BookDataController {
    @Autowired
    IBookService bookService;

    /**
     * Returns book data by provided ID.
     * @param bookId the book ID
     * @return the book data
     * @throws BookNotFoundException
     */
    @GetMapping("/{bookId:[A-Za-z0-9\\-]+}")
    public Book getBookById(@PathVariable String bookId) 
    throws BookNotFoundException {
        // Get and return the book data
        return bookService.getById(bookId);
    }

    /**
     * Creates and inserts new book data
     * @param form the book data, provided as JSON
     * @return the new book
     * @throws BookNotProvidedException
     */
    @PostMapping
    public Book createBook(
        @RequestBody BookForm form) throws BookNotProvidedException {
        // Create and return new book
        return bookService.create(form);
    }

    /**
     * Update a book, by provided ID
     * @param bookId the book ID
     * @param form the book information to update
     * @return the updated book information
     * @throws BookNotFoundException
     * @throws BookNotProvidedException
     */
    @PutMapping("/{bookId:[A-Za-z0-9\\-]+}")
    public Book updateBook(
        @PathVariable String bookId,
        @RequestBody BookForm form) throws 
        BookNotFoundException, BookNotProvidedException {
        // Update and return the book data
        return bookService.update(bookId, form);
    }

    /**
     * Delete the book data, by provided ID
     * @param bookId the book ID
     * @return a confirmation that the book has been deleted
     * @throws BookNotFoundException
     */
    @DeleteMapping("/{bookId:[A-Za-z0-9\\-]+}")
    public ApiMessage deleteBook(
        @PathVariable String bookId) 
    throws BookNotFoundException {
        // Delete the book
        bookService.delete(bookId);

        // Return a confirmation message
        return new ApiMessage(
            "This book has been deleted");
    }
}
