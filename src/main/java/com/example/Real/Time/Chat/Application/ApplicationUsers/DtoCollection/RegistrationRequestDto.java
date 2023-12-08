package com.example.Real.Time.Chat.Application.ApplicationUsers.DtoCollection;

import lombok.Data;

@Data
public class RegistrationRequestDto {
    private String firstName;
    private String lastName;
    private String country;
    private String phoneNumber;
    private String password;
    private String email;
    private String userName;
}
