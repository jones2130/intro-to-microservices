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
import com.introtomicroservices.bookdatahandler.models.ApiMessage;
import com.introtomicroservices.bookdatahandler.models.Book;
import com.introtomicroservices.bookdatahandler.services.IBookService;

@RestController
@RequestMapping("/book/data")
public class BookDataController {
    @Autowired
    IBookService bookService;

    @GetMapping("/{bookId:[A-Za-z0-9\\-]+}")
    public Book getBookById(@PathVariable String bookId) 
    throws BookNotFoundException {
        return bookService.getById(bookId);
    }

    @PostMapping
    public Book createBook(
        @RequestBody BookForm form) throws BookNotProvidedException {
        return bookService.create(form);
    }

    @PutMapping("/{bookId:[A-Za-z0-9\\-]+}")
    public Book updateBook(
        @PathVariable String bookId,
        @RequestBody BookForm form) throws 
        BookNotFoundException, BookNotProvidedException {
        return bookService.update(bookId, form);
    }

    @DeleteMapping("/{bookId:[A-Za-z0-9\\-]+}")
    public ApiMessage deleteBook(
        @PathVariable String bookId) 
    throws BookNotFoundException {
        bookService.delete(bookId);
        return new ApiMessage(
            "This book has been deleted");
    }
}
