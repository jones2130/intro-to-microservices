package com.introtomicroservices.search.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.introtomicroservices.search.repositories.BookRepository;
import com.introtomicroservices.shared.models.OffsetBasedPageRequest;
import com.introtomicroservices.shared.models.mongodb.Book;

/**
 * @author jjones
 */
@Service
public class SearchService implements ISearchService {
    @Autowired
    BookRepository repository;

    /**
     * Find all books the fit a search criteria
     * @param query the search query string
     * @param offset the list offset
     * @param limit the maximum size of the search query
     * @return the search results list
     */
    @Override
    public List<Book> findBooks(String query, int offset, int limit) {
        // If the limit is zero, then return all books the fit the search
        // criteria
        if(limit == 0) {
            return findAllBooks(query);
        }

        // Create a placeholder for the book page results
        Page<Book> books;
        
        // If no query string is provided, then get all book data, with a paginated
        // list result, otherwise query by the search string
        if(query == null) {
            books = repository.findAll(new OffsetBasedPageRequest(offset, limit));
        } else {
            books = repository.query(query, new OffsetBasedPageRequest(offset, limit));
        }

        // Convert the paginated results to an array list
        return books.toList();
    }

    /**
     * Search for book data, without regard to list limits
     * @param query the search query string
     * @return the book list results
     */
    private List<Book> findAllBooks(String query) {
        // If no search query string is provided get all book data
        if(query == null) {
            return repository.findAll();
        }

        // Get the list results by search query string
        return repository.query(query);
    }
}
