package com.example.card.repository;

import com.example.card.model.Card;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends MongoRepository<Card, Integer> {
    List<Card> findByCustomerId(int customerId);
}
