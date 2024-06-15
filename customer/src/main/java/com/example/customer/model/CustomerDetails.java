package com.example.customer.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Document("CustomerDetails")
@Getter
@Setter
@ToString
public class CustomerDetails {
    @Id
    private String id;
    private Customer customer;
    private List<Loan> loans;
    private List<Card> cards;

    public CustomerDetails() {
        this.id = UUID.randomUUID().toString();
    }
}