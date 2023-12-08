package com.example.Real.Time.Chat.Application.ApplicationUsers.Models;

import jakarta.persistence.*;
import lombok.Data;


import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
public class UserEntity {
    //System will maintain these properties(UNMAPPED_FROM_DTO)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;
    private String passwordHash;
    private UserRole userRole;
    private UserStatus userStatus;
    private Timestamp createdAT;
    private Timestamp updatedAT;

    //Mapped From DTO
    private String firstName;
    private String lastName;
    private String userName;
    private String country;
    private String phoneNumber;
    private String email;

    public void addSystemPropertiesForNewUser(UserEntity userEntity){
        userEntity.setCreatedAT(Timestamp.from(Instant.now()));
        userEntity.setUserRole(UserRole.REGULAR_USER);
        userEntity.setUserStatus(UserStatus.PENDING_VERIFICATION);
    }
}
