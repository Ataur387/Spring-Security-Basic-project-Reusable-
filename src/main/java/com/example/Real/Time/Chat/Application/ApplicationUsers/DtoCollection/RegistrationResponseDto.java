package com.example.Real.Time.Chat.Application.ApplicationUsers.DtoCollection;

import lombok.Data;

@Data
public class RegistrationResponseDto {
    private String response;
    public RegistrationResponseDto(){
        this.response = "Please help us by checking your email for verification link.";
    }
}
