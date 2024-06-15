package com.example.customer.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document("Card")
@Getter @Setter @ToString
public class Card {

    @Id
    private String id;
    private ObjectId customerId;
    private String cardNumber;
    private String cardType;
    private int totalLimit;
    private int amountUsed;
    private int availableAmount;
    private String createAt;

    public Card() {
        this.id = UUID.randomUUID().toString();
    }
}
