package com.example.customer.controller;

import com.example.customer.common.Response;
import com.example.customer.model.Customer;
import com.example.customer.repository.CustomerRepository;
import com.example.customer.service.CustomerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RequestMapping("customer")
@RestController
public class CustomerController {

    private final Logger logger = LogManager.getLogger(CustomerController.class);

    @Autowired
    CustomerService customerService;

    @PostMapping("signup")
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer){
        logger.info("Customer received {}", customer);
        if (customerService.findByEmail(customer.getEmail()) != null)
            return Response.response(null, "Email has been signed up", 400);
        Customer _customer = customerService.save(customer);
        logger.info("Customer created {}", _customer);
        return Response.response(_customer, "success", 200);
    }



}
