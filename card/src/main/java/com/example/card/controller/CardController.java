package com.example.card.controller;


import com.example.card.config.CardServiceConfig;
import com.example.card.model.Card;
import com.example.card.model.Customer;
import com.example.card.common.Response;
import com.example.card.repository.CardRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CardController {

    private static final Logger logger = LogManager.getLogger(CardController.class);

    @Autowired(required = false)
    CardRepository cardRepository;

    @Autowired(required = false)
    CardServiceConfig cardServiceConfig;

    @PostMapping("cardDetails")
    public ResponseEntity<?> getCardDetails(@RequestHeader("correlation-id")  String correlationId, @RequestBody Customer customer) {
        logger.info("Stating getCardDetails .....");
        List<Card> cards = cardRepository.findByCustomerId(customer.getCustomerId());
        logger.info("Ending getCardDetails .....");
        if (cards!=null)
            return Response.response(cards, "success", 200);
        return Response.response(null, "No cards found", 200);
    }

    @GetMapping("test")
    public String test() {
        return "test";}
}
