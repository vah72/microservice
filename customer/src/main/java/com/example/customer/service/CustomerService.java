package com.example.customer.service;

import com.example.customer.model.Customer;

public interface CustomerService {
    public Customer findByEmail(String email);

    public Customer save(Customer customer);
}
