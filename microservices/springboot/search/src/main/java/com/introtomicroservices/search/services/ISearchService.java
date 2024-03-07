package com.introtomicroservices.search.services;

import java.util.List;

import com.introtomicroservices.search.models.Book;

public interface ISearchService {
    List<Book> findBooks(String query, int offset, int limit);
}
