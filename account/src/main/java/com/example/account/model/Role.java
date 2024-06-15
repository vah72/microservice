package com.example.account.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document("role")
@Data
public class Role {
    @Id
    private String id;
    private String name;

    public Role(String name) {
        this.name = name;
    }

    public Role() {
        this.id = UUID.randomUUID().toString();
    }
}
