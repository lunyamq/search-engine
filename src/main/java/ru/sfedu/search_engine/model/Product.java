package ru.sfedu.search_engine.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Products")
public record Product(@Id Long id, String name, double price) {

}
