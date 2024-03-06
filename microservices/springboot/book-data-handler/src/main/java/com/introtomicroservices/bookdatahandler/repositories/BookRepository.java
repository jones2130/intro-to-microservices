package com.introtomicroservices.bookdatahandler.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.introtomicroservices.bookdatahandler.models.Book;

public interface BookRepository extends MongoRepository<Book, String>{
    
}
