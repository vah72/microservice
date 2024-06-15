package com.example.customer.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Document("Loan")
@Getter @Setter @ToString
public class Loan {

    @Id
    private String id;
    private ObjectId customerId;
    private Date startAt;
    private String loanType;
    private int totalLoan;
    private int amountPaid;
    private int outstandingAmount;
    private Date createAt;

    public Loan() {
        this.id = UUID.randomUUID().toString();
    }
}
