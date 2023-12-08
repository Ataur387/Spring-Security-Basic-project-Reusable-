package com.example.Real.Time.Chat.Application.ApplicationUsers.Repositories;

import com.example.Real.Time.Chat.Application.ApplicationUsers.Models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> getUserByUserName(String userName);
    Optional<UserEntity> getUserByEmail(String email);
}
