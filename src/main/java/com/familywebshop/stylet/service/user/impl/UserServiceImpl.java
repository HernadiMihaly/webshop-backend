package com.familywebshop.stylet.service.user.impl;

import com.familywebshop.stylet.dto.user.RequestUserDto;
import com.familywebshop.stylet.model.user.ConfirmationToken;
import com.familywebshop.stylet.model.user.Role;
import com.familywebshop.stylet.model.user.User;
import com.familywebshop.stylet.repository.user.UserRepository;
import com.familywebshop.stylet.service.user.ConfirmationTokenService;
import com.familywebshop.stylet.service.user.EmailService;
import com.familywebshop.stylet.service.user.UserService;
import com.familywebshop.stylet.util.CustomValidator;
import com.familywebshop.stylet.util.EmailBuilder;
import com.familywebshop.stylet.util.ModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final CustomValidator customValidator;

    private final ConfirmationTokenService confirmationTokenService;

    private final EmailService emailService;

    private final EmailBuilder emailBuilder;

    @Override
    public User signUp(RequestUserDto requestUserDTO) throws IllegalArgumentException {
        customValidator.validateUserData(requestUserDTO);

        requestUserDTO.setPassword(passwordEncoder.encode(requestUserDTO.getPassword()));

        User user = ModelMapper.getInstance()
                .mapDtoToEntity(requestUserDTO, User.class);
        user.setRole(Role.ROLE_USER);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = ConfirmationToken.builder()
                .token(token)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusDays(3))
                .user(user)
                .build();

        userRepository.save(user);

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        String link = "http://localhost:8080/identity/registration/confirm?token=" + token;
        emailService.send(
                user.getEmail(), emailBuilder.buildEmail(user.getFirstName(), link)
        );

        return user;
    }

    @Override
    public Role getRole(String username){
        User user = userRepository.findByEmail(username).orElseThrow();

        return user.getRole();
    }

    @Override
    public int enableUser(String email) {
        return userRepository.enableUser(email);
    }
}
