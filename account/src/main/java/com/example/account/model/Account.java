package com.example.account.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.bson.types.ObjectId;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Document("account")
@Data
public class Account {
    @Id
    private String id;
    private String username;
    private String password;
    private String customerId;
    private Set<Role> roles = new HashSet<>();
    private String branchAddress;
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDate createdAt;

    private boolean isActive;

    public Account() {
        this.id = UUID.randomUUID().toString();
    }
}


