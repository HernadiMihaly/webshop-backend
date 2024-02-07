package com.familywebshop.stylet.service.impl;

import com.familywebshop.stylet.dto.RequestUserDto;
import com.familywebshop.stylet.model.Role;
import com.familywebshop.stylet.model.User;
import com.familywebshop.stylet.repository.UserRepository;
import com.familywebshop.stylet.service.UserService;
import com.familywebshop.stylet.util.CustomValidator;
import com.familywebshop.stylet.util.ModelMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final CustomValidator customValidator;

    public User signUp(RequestUserDto requestUserDTO) throws IllegalArgumentException {
        customValidator.validateUserData(requestUserDTO);

        requestUserDTO.setPassword(passwordEncoder.encode(requestUserDTO.getPassword()));

        User user = ModelMapper.getInstance()
                .mapDtoToEntity(requestUserDTO, User.class);
        user.setRole(Role.ROLE_USER);

        userRepository.save(user);

        return user;
    }

    public Role getRole(String username){
        User user = userRepository.findByEmail(username).orElseThrow();

        return user.getRole();
    }
}
