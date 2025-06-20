package ru.sfedu.search_engine.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.sfedu.search_engine.model.Product;

// extends CRUD operations from MongoRepository
public interface ProductRepository extends MongoRepository<Product, Long> {

}
