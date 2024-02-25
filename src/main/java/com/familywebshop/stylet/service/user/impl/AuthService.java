package com.familywebshop.stylet.service.user.impl;

import com.familywebshop.stylet.dto.user.AuthenticationResponse;
import com.familywebshop.stylet.dto.user.RequestUserDto;
import com.familywebshop.stylet.model.user.User;
import com.familywebshop.stylet.repository.user.UserRepository;
import com.familywebshop.stylet.service.user.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    public AuthenticationResponse authenticate(RequestUserDto userDto) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(userDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("No user with email: " + userDto.getEmail()));

        return AuthenticationResponse.builder()
                .token(jwtService.generateToken(user))
                .build();
    }
}
