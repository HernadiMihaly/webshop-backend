package com.familywebshop.stylet.service.impl;

import com.familywebshop.stylet.dto.RequestUserDto;
import com.familywebshop.stylet.model.Role;
import com.familywebshop.stylet.model.User;
import com.familywebshop.stylet.repository.UserRepository;
import com.familywebshop.stylet.service.UserService;
import com.familywebshop.stylet.util.ModelMapper;
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

    public void signUp(RequestUserDto requestUserDTO) {
        requestUserDTO.setPassword(passwordEncoder.encode(requestUserDTO.getPassword()));

        User user = ModelMapper.getInstance()
                .mapDtoToEntity(requestUserDTO, User.class);
        user.setRole(Role.ROLE_USER);

        userRepository.save(user);
    }

    public Role getRole(String username){
        User user = userRepository.findByEmail(username).orElseThrow();

        return user.getRole();
    }

}
