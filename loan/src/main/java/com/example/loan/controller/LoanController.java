package com.example.loan.controller;

import com.example.loan.common.Response;
import com.example.loan.config.LoanServiceConfig;
import com.example.loan.model.Customer;
import com.example.loan.model.Loan;
import com.example.loan.repository.LoanRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LoanController {

    private final static Logger logger = LogManager.getLogger(LoanController.class);

    @Autowired(required = false)
    LoanRepository loanRepository;

    @Autowired(required = false)
    LoanServiceConfig loanServiceConfig;

    @PostMapping("loanDetails")
    public ResponseEntity<?> getLoanDetails(@RequestHeader("correlation-id")String correlationId, @RequestBody Customer customer){
        logger.info("Stating getLoanDetails ....");
        List<Loan> loans = loanRepository.findByCustomerId(customer.getCustomerId());
        logger.info("Ending getLoanDetails ...");
        if (loans != null)
            return Response.response(loans, "success", 200);
        return Response.response(null, "No loans found", 200);
    }
}
