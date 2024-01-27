package com.familywebshop.stylet.controller;

import com.familywebshop.stylet.dto.RequestUserDto;
import com.familywebshop.stylet.dto.SubscribedUserDto;
import com.familywebshop.stylet.service.RegistrationService;
import org.hibernate.PropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/identity")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping(path = "/register")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> register(@RequestBody RequestUserDto requestUserDTO){
        try {
            registrationService.register(requestUserDTO);

            return ResponseEntity.ok("User successfully registered!");
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            if (dataIntegrityViolationException.getMessage().contains("Duplicate entry")){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("User already registered!");
            }

            return ResponseEntity.badRequest().body("Property cannot be null!");
        } catch (IllegalArgumentException illegalArgumentException){
            return ResponseEntity.badRequest().body(illegalArgumentException.getMessage());
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping(path = "/subscribe")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> subscribe(@RequestBody SubscribedUserDto subscribedUserDto){
        try {
            registrationService.subscribe(subscribedUserDto);

            return ResponseEntity.ok("User successfully subscribed!");
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            if (dataIntegrityViolationException.getMessage().contains("Duplicate entry")){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("User already subscribed!");
            }

            return ResponseEntity.badRequest().body("Property cannot be null!");
        } catch (IllegalArgumentException illegalArgumentException){
            return ResponseEntity.badRequest().body(illegalArgumentException.getMessage());
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

}
