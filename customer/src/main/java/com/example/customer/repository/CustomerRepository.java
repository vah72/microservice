package com.example.customer.repository;

import com.example.customer.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {
    @Query("{'email': '?0'}")
    Customer findByEmail(String email);

    Customer save(Customer customer);
}
