package com.familywebshop.stylet.service.impl;

import com.familywebshop.stylet.dto.UserDTO;
import com.familywebshop.stylet.service.RegistrationService;
import com.familywebshop.stylet.service.UserService;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final UserService userService;

    public RegistrationServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void register(UserDTO userDTO) {
        if (EmailValidator.getInstance().isValid(userDTO.getEmail())) {
            userService.signUp(userDTO);
        } else throw new IllegalStateException("Email is not valid!");
    }
}
