package com.example.loan.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("Loan")
@Data
public class Loan {
    @Id
    private ObjectId loanId;
    private ObjectId customerId;
    private Date startAt;
    private int loanType;
    private int totalLoan;
    private int amountPaid;
    private int outstandingAmount;
    private Date createAt;

}
