package com.example.customer.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.bson.types.ObjectId;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.UUID;

@Document("Customer")
@Data
public class Customer {
    @Id
    private String id;
    private String name;
    private String email;
    private String mobileNumber;
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDate createdAt;

    public Customer() {
        this.id = UUID.randomUUID().toString();
    }
}
