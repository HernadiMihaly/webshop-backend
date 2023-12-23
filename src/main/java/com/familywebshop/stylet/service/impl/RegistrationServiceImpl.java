package com.familywebshop.stylet.service.impl;

import com.familywebshop.stylet.dto.RequestUserDto;
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
    public void register(RequestUserDto requestUserDTO) {
        if (EmailValidator.getInstance().isValid(requestUserDTO.getEmail())) {
            userService.signUp(requestUserDTO);
        } else throw new IllegalStateException("Email is not valid!");
    }

}
