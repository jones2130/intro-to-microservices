package com.introtomicroservices.bookdatahandler.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.introtomicroservices.bookdatahandler.exceptions.BookNotFoundException;
import com.introtomicroservices.bookdatahandler.exceptions.BookNotProvidedException;
import com.introtomicroservices.bookdatahandler.models.Book;
import com.introtomicroservices.bookdatahandler.repositories.BookRepository;

@Service
public class BookService implements IBookService {
    @Autowired
    BookRepository repository;

    @Override
    public Book getById(String id) throws BookNotFoundException {
        return repository.findById(id)
            .orElseThrow(() -> new BookNotFoundException());
    }

    @Override
    public Book create(Book book) throws BookNotProvidedException {
        if(book == null) {
            throw new BookNotProvidedException();
        }

        return repository.save(book);
    }

    @Override
    public Book update(String id, Book book) throws BookNotFoundException, BookNotProvidedException {
        if(book == null) {
            throw new BookNotProvidedException();
        }

        return repository.findById(id)
            .map((Book updatedBook) -> {
                updatedBook.setTitle(book.getTitle());
                updatedBook.setAuthor(book.getAuthor());
                updatedBook.setDescription(book.getDescription());

                return repository.save(updatedBook);
            })
            .orElseThrow(() -> new BookNotFoundException());
    }

    @Override
    public void delete(String id) throws BookNotFoundException {
         repository.findById(id)
            .map((Book book) -> {
                repository.delete(book);
                return null;
            })
            .orElseThrow(() -> new BookNotFoundException());
    }
}
