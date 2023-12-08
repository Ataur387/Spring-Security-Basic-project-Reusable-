package com.example.Real.Time.Chat.Application.Security.JWT;

import lombok.Data;

@Data
public class JwtException {
    private String message;

    public JwtException(String message) {
        this.message = message;
    }
}