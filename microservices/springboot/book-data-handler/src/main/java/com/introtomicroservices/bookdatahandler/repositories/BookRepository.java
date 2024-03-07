package com.introtomicroservices.bookdatahandler.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.introtomicroservices.shared.models.mongodb.Book;

/**
 * @author jjones
 */
@Repository
public interface BookRepository extends MongoRepository<Book, String>{
    
}
