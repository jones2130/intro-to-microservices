package com.introtomicroservices.bookdatahandler.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.introtomicroservices.bookdatahandler.exceptions.BookNotFoundException;
import com.introtomicroservices.bookdatahandler.exceptions.BookNotProvidedException;
import com.introtomicroservices.bookdatahandler.forms.BookForm;
import com.introtomicroservices.bookdatahandler.repositories.BookRepository;
import com.introtomicroservices.shared.models.mongodb.Book;

/**
 * @author jjones
 */
@Service
public class BookService implements IBookService {
    @Autowired
    BookRepository repository;

    /**
     * Get the book data from the database by ID
     * @param id the book ID
     * @return the book data
     * @throws BookNotFoundException
     */
    @SuppressWarnings("null")
    @Override
    public Book getById(String id) throws BookNotFoundException {
        // Find a book, if one does not exist then throw an exception
        return repository.findById(id)
            .orElseThrow(() -> new BookNotFoundException());
    }

    /**
     * Create a new book from from data
     * @param form the book form data
     * @return the new book data
     * @throws BookNotProvidedException
     */
    @Override
    public Book create(BookForm form) throws BookNotProvidedException {
        // If the form data does not exist, the throw an exception
        if(form == null) {
            throw new BookNotProvidedException();
        }

        // Create a new book record and insert the form data
        Book book = new Book();
        
        book.setId(UUID.randomUUID().toString());
        book.setTitle(form.getTitle());
        book.setAuthor(form.getAuthor());
        book.setDescription(form.getDescription());

        // Save and return the book
        return repository.save(book);
    }

    /**
     * Update and return a book, by ID, from form data
     * @param id the book ID
     * @param form the book form data
     * @return the updated book data
     * @throws BookNotFoundException, BookNotProvidedException
     */
    @SuppressWarnings("null")
    @Override
    public Book update(String id, BookForm form) throws 
        BookNotFoundException, BookNotProvidedException {
        // If the form data is not provided, then throw an exception
        if(form == null) {
            throw new BookNotProvidedException();
        }

        // Get the book data, if it is not foud then throw an exception
        return repository.findById(id)
            .map((Book book) -> {
                // Update the book data with the book form data
                book.setTitle(form.getTitle());
                book.setAuthor(form.getAuthor());
                book.setDescription(form.getDescription());

                // Save and return the updated book
                return repository.save(book);
            })
            .orElseThrow(() -> new BookNotFoundException());
    }

    /**
     * Delete a book, by ID
     * @param id the book ID
     * @throws BookNotFoundException
     */
    @SuppressWarnings("null")
    @Override
    public void delete(String id) throws BookNotFoundException {
        // Get the book data, if it is not found the throw an exception
         Book book = repository.findById(id)
            .orElseThrow(() -> new BookNotFoundException());
        // Delete the book
        repository.delete(book);
    }
}
