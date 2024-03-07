package com.introtomicroservices.search.services;

import java.util.List;

import com.introtomicroservices.shared.models.mongodb.Book;

/**
 * @author jjones
 */
public interface ISearchService {
    List<Book> findBooks(String query, int offset, int limit);
}
