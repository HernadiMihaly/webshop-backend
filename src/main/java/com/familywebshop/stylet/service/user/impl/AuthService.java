package com.familywebshop.stylet.service.user.impl;

import com.familywebshop.stylet.dto.user.AuthenticationResponse;
import com.familywebshop.stylet.dto.user.RequestUserDto;
import com.familywebshop.stylet.exception.InvalidCredentialsException;
import com.familywebshop.stylet.model.user.User;
import com.familywebshop.stylet.repository.user.UserRepository;
import com.familywebshop.stylet.service.user.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponse authenticate(RequestUserDto userDto) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(userDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("No user with email: " + userDto.getEmail()));

        if (user.getEnabled().equals(true)) {
            if (passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
                return AuthenticationResponse.builder()
                        .token(jwtService.generateToken(user))
                        .build();
            } else
                throw new InvalidCredentialsException("Invalid password!");
        } else
            throw new UsernameNotFoundException("Please activate your user!");
    }
}
