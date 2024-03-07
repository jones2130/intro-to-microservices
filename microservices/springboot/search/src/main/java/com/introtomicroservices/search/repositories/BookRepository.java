package com.introtomicroservices.search.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.introtomicroservices.search.models.Book;

public interface BookRepository extends MongoRepository<Book, String>{
    public static final String TEXT_SEARCH_QUERY = "{ $or: ["+
    "{'title': {$regex:?0,$options:'i'}}, "+
    "{'author': {$regex:?0,$options:'i'}}, "+
    "{'description': {$regex:?0,$options:'i'}}"+
    "]}";

    @Query(value = TEXT_SEARCH_QUERY)
    Page<Book> query(String query, Pageable page);

    @Query(value = TEXT_SEARCH_QUERY)
    List<Book> query(String query);
}
