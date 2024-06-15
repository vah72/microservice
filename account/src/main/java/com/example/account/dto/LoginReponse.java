package com.example.account.dto;

import lombok.Data;

import java.util.List;

@Data
public class LoginReponse {
    String token;
    String type = "Bearer";
    String id;
    String username;
    List<String> roles;

    public LoginReponse(String token, String username, String id, List<String> roles) {
        this.token = token;
        this.username = username;
        this.type = "Bearer";
        this.id = id;
        this.roles = roles;
    }
}
