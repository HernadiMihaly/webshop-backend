package com.familywebshop.stylet.exception;

import com.familywebshop.stylet.dto.user.AuthenticationResponse;

public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException(String message) {
        super(message);
    }
}
