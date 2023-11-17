package com.familywebshop.stylet.service.impl;

import com.familywebshop.stylet.dto.RequestUserDTO;
import com.familywebshop.stylet.model.Role;
import com.familywebshop.stylet.model.User;
import com.familywebshop.stylet.repository.UserRepository;
import com.familywebshop.stylet.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void signUp(RequestUserDTO requestUserDTO) {
        requestUserDTO.setPassword(passwordEncoder.encode(requestUserDTO.getPassword()));
        userRepository.save(mapDtoToUser(requestUserDTO));
    }

    private User mapDtoToUser(RequestUserDTO requestUserDTO){
        return User.builder()
                .email(requestUserDTO.getEmail())
                .firstName(requestUserDTO.getFirstName())
                .lastName(requestUserDTO.getLastName())
                .password(requestUserDTO.getPassword())
                .role(Role.ROLE_USER)
                .build();
    }

    public Role getRole(String username){
        User user = userRepository.findByEmail(username).orElseThrow();

        return user.getRole();
    }
}
