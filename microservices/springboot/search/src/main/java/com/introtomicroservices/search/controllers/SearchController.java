package com.introtomicroservices.search.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.introtomicroservices.search.services.ISearchService;
import com.introtomicroservices.shared.models.mongodb.Book;
import com.introtomicroservices.shared.models.mongodb.BookListItem;

/**
 * @author jjones
 */
@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    ISearchService searchService;

    /**
     * Search for book items by title, author, and description text
     * @param search the search query string
     * @param offset the results list offset
     * @param limit the maximum size for the list
     * @return the list of book data the fit the search query
     */
    @GetMapping
    public List<BookListItem> searchBooks(
        @RequestParam(value="search", required = false) String search,
        @RequestParam(value="offset", defaultValue = "0") int offset,
        @RequestParam(value="limit", defaultValue = "10") int limit) {
        // Find all books the fit the search query, with the limit and offset
        // included
        List<Book> books = searchService.findBooks(search, offset, limit);

        // For each result return only the book ID and title information, return
        // the list
        List<BookListItem> items = new ArrayList<>();
        for(Book book: books) {
            BookListItem item = new BookListItem(book.getId(), book.getTitle());
            items.add(item);
        }

        return items;
    }
}
