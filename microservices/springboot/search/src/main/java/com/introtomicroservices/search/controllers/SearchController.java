package com.introtomicroservices.search.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.introtomicroservices.search.models.Book;
import com.introtomicroservices.search.models.BookListItem;
import com.introtomicroservices.search.services.ISearchService;

@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    ISearchService searchService;

    @GetMapping
    public List<BookListItem> searchBooks(
        @RequestParam(value="search", required = false) String search,
        @RequestParam(value="offset", defaultValue = "0") int offset,
        @RequestParam(value="limit", defaultValue = "10") int limit) {
        List<Book> books = searchService.findBooks(search, offset, limit);
        List<BookListItem> items = new ArrayList<>();

        for(Book book: books) {
            BookListItem item = new BookListItem(book.getId(), book.getTitle());
            items.add(item);
        }

        return items;
    }
}
