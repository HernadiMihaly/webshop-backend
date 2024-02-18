package com.familywebshop.stylet.controller;

import com.familywebshop.stylet.dto.RequestUserDto;
import com.familywebshop.stylet.dto.SubscribedUserDto;
import com.familywebshop.stylet.service.ConfirmationTokenService;
import com.familywebshop.stylet.service.RegistrationService;
import com.familywebshop.stylet.service.impl.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/identity")
@RequiredArgsConstructor
public class UserController {

    private final RegistrationService registrationService;

    private final AuthService authService;

    @PostMapping(path = "/register")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> register(@RequestBody RequestUserDto requestUserDTO){
        try {
            return ResponseEntity.ok(registrationService.register(requestUserDTO));
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

            return ResponseEntity.ok().build();
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

    @PostMapping(path = "/login")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> login(@RequestBody RequestUserDto requestUserDTO){
        try {
            return ResponseEntity.ok(authService.authenticate(requestUserDTO));
        } catch (UsernameNotFoundException usernameNotFoundException){
            return ResponseEntity.notFound().build();
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(path = "/registration/confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }
}
