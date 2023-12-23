package com.familywebshop.stylet.controller;

import com.familywebshop.stylet.dto.RequestUserDto;
import com.familywebshop.stylet.service.RegistrationService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> register(@RequestBody RequestUserDto requestUserDTO){
        registrationService.register(requestUserDTO);
        return ResponseEntity.ok("User successfully registered!");
    }

}
