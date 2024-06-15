package com.example.account.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.ResponseEntity;

@Getter @Setter @ToString
public class Response {
    Object data;
    String message;
    int status;

    public Response(Object data, String message, int status) {
        this.data = data;
        this.message = message;
        this.status = status;
    }

    public static ResponseEntity<Response> response(Object data, String message, int status) {
        return ResponseEntity.ok(new Response(data, message, status));
    }
}
