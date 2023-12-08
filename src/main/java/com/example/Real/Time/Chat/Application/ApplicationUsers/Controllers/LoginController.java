package com.example.Real.Time.Chat.Application.ApplicationUsers.Controllers;

import com.example.Real.Time.Chat.Application.ApplicationUsers.DtoCollection.*;
import com.example.Real.Time.Chat.Application.ApplicationUsers.Services.UserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.Registration;

@RestController
public class LoginController {
    private final UserDetailsService userDetailsService;

    public LoginController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/home")
    public ResponseEntity<BaseResponseEntity> getHomePage(){
        BaseResponseEntity baseResponseEntity = new BaseResponseEntity();
        baseResponseEntity.setResponseBody("Welcome to home page.");
        return ResponseEntity.ok(baseResponseEntity);
    }
    @PostMapping("/authenticate")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDto dto){
        return new ResponseEntity<>(userDetailsService.processLoginRequest(dto.getUsername(),dto.getPassword()), HttpStatus.OK);
    }
    @PostMapping("/register")
    public ResponseEntity<RegistrationResponseDto> register(@RequestBody() RegistrationRequestDto requestDto){
        return new ResponseEntity<>(userDetailsService.registerUser(requestDto), HttpStatus.OK);
    }
    @GetMapping ("/register/verify")
    public ResponseEntity<String> register(@RequestParam("email") String email){
        userDetailsService.activateNewUser(email);
        return new ResponseEntity<>("Welcome Abroad. Your account is activated now. Login to continue", HttpStatus.OK);
    }
}
