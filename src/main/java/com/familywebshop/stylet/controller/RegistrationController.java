package com.familywebshop.stylet.controller;

import com.familywebshop.stylet.dto.RequestUserDTO;
import com.familywebshop.stylet.service.RegistrationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {
    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping(path = "/identity/register")
    public String register(@RequestBody RequestUserDTO requestUserDTO){
        registrationService.register(requestUserDTO);
        return "User added";
    }
}
