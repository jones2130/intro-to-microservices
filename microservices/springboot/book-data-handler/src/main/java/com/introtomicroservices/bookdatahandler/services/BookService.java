package com.introtomicroservices.bookdatahandler.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.introtomicroservices.bookdatahandler.exceptions.BookNotFoundException;
import com.introtomicroservices.bookdatahandler.exceptions.BookNotProvidedException;
import com.introtomicroservices.bookdatahandler.forms.BookForm;
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
    public Book create(BookForm form) throws BookNotProvidedException {
        if(form == null) {
            throw new BookNotProvidedException();
        }

        Book book = new Book();
        
        book.setId(UUID.randomUUID().toString());
        book.setTitle(form.getTitle());
        book.setAuthor(form.getAuthor());
        book.setDescription(form.getDescription());

        return repository.save(book);
    }

    @Override
    public Book update(String id, BookForm form) throws BookNotFoundException, BookNotProvidedException {
        if(form == null) {
            throw new BookNotProvidedException();
        }

        return repository.findById(id)
            .map((Book book) -> {
                book.setTitle(form.getTitle());
                book.setAuthor(form.getAuthor());
                book.setDescription(form.getDescription());

                return repository.save(book);
            })
            .orElseThrow(() -> new BookNotFoundException());
    }

    @Override
    public void delete(String id) throws BookNotFoundException {
         Book book = repository.findById(id)
            .orElseThrow(() -> new BookNotFoundException());
        repository.delete(book);
    }
}
