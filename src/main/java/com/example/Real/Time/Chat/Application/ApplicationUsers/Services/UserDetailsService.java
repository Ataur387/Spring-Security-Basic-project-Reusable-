package com.example.Real.Time.Chat.Application.ApplicationUsers.Services;
import com.example.Real.Time.Chat.Application.ApplicationUsers.DtoCollection.LoginResponseDTO;
import com.example.Real.Time.Chat.Application.ApplicationUsers.DtoCollection.RegistrationRequestDto;
import com.example.Real.Time.Chat.Application.ApplicationUsers.DtoCollection.RegistrationResponseDto;
import com.example.Real.Time.Chat.Application.ApplicationUsers.Models.UserEntity;
import com.example.Real.Time.Chat.Application.ApplicationUsers.Models.UserRole;
import com.example.Real.Time.Chat.Application.ApplicationUsers.Models.UserStatus;
import com.example.Real.Time.Chat.Application.ApplicationUsers.Repositories.UserRepository;
import com.example.Real.Time.Chat.Application.Helpers.BaseDTOMapper;
import com.example.Real.Time.Chat.Application.Helpers.MailSender.LocalMailSender;
import com.example.Real.Time.Chat.Application.Security.JWT.JwtHelper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;
    private final JwtHelper jwtHelper;
    private final PasswordEncoder passwordEncoder;
    private final BaseDTOMapper baseDTOMapper;
    private final LocalMailSender localMailSender;
    public UserDetailsService(UserRepository userRepository, JwtHelper jwtHelper, PasswordEncoder passwordEncoder, BaseDTOMapper baseDTOMapper, LocalMailSender localMailSender) {
        this.userRepository = userRepository;
        this.jwtHelper = jwtHelper;
        this.passwordEncoder = passwordEncoder;
        this.baseDTOMapper = baseDTOMapper;
        this.localMailSender = localMailSender;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            Optional<UserEntity> userEntityOptional = userRepository.getUserByUserName(username);
            UserEntity userEntity = userEntityOptional.orElseThrow(() ->
                    new UsernameNotFoundException("User not found with username: " + username));
            return buildUserDetails(userEntity);
    }
    public UserDetails buildUserDetails(UserEntity userEntity){
        String userName = userEntity.getUserName();
        String password = userEntity.getPasswordHash();
        UserRole userRole = userEntity.getUserRole();
        SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(userRole.toString());
        return User.builder().username(userName).password(password).authorities(grantedAuthority).build();
    }
    public LoginResponseDTO processLoginRequest(String userName, String password) {
        Optional<UserEntity> userEntityOptional = userRepository.getUserByUserName(userName);
        UserEntity userEntity = userEntityOptional.orElseThrow(() ->
                new UsernameNotFoundException("User not found with username: " + userName));

        String token = jwtHelper.generateToken(buildUserDetails(userEntity));
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        loginResponseDTO.setToken(token);
        return loginResponseDTO;
    }
    public RegistrationResponseDto registerUser(RegistrationRequestDto requestDto){
        RegistrationResponseDto responseDto = new RegistrationResponseDto();
        UserEntity userEntity = baseDTOMapper.convertToEntity(requestDto, UserEntity.class);
        userEntity.addSystemPropertiesForNewUser(userEntity);
        userEntity.setPasswordHash(passwordEncoder.encode(requestDto.getPassword()));
        localMailSender.sendRegistrationVerificationMail(requestDto.getEmail(), "Account Verification", "Link");
        userRepository.save(userEntity);
        return responseDto;
    }
    public void activateNewUser(String email){
        Optional<UserEntity> userEntity = userRepository.getUserByEmail(email);
        if(userEntity.isPresent()){
            UserEntity user = userEntity.get();
            user.setUserStatus(UserStatus.ENABLED);
            userRepository.save(user);
        }
    }
}
