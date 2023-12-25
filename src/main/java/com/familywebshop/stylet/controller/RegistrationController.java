package com.familywebshop.stylet.controller;

import com.familywebshop.stylet.dto.RequestUserDto;
import com.familywebshop.stylet.dto.SubscribedUserDto;
import com.familywebshop.stylet.service.RegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

@RestController
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping(path = "/identity/register")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> register(@RequestBody RequestUserDto requestUserDTO){
        registrationService.register(requestUserDTO);
        return ResponseEntity.ok("User successfully registered!");
    }

    @PostMapping(path = "/identity/subscribe")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> subscribe(@RequestBody SubscribedUserDto subscribedUserDto){
        try {
            registrationService.subscribe(subscribedUserDto);

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e){
            if (e.getMessage().contains("Duplicate entry")){

                return new ResponseEntity<>(HttpStatus.CONFLICT);

            }

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }

    }

}
