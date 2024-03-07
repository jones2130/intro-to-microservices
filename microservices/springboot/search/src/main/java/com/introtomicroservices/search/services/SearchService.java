package com.introtomicroservices.search.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.introtomicroservices.search.models.Book;
import com.introtomicroservices.search.models.OffsetBasedPageRequest;
import com.introtomicroservices.search.repositories.BookRepository;

@Service
public class SearchService implements ISearchService {
    Logger logger = LoggerFactory.getLogger(SearchService.class);

    @Autowired
    BookRepository repository;

    @Override
    public List<Book> findBooks(String query, int offset, int limit) {
        logger.info(query);
        if(limit == 0) {
            return findAllBooks(query);
        }

        Page<Book> books;
        
        if(query == null) {
            books = repository.findAll(new OffsetBasedPageRequest(offset, limit));
        } else {
            books = repository.query(query, new OffsetBasedPageRequest(offset, limit));
        }

        return books.toList();
    }

    private List<Book> findAllBooks(String query) {
        if(query == null) {
            return repository.findAll();
        }

        return repository.query(query);
    }
}
