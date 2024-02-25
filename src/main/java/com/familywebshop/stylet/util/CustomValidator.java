package com.familywebshop.stylet.util;

import com.familywebshop.stylet.dto.user.RequestUserDto;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;

@Component
public class CustomValidator {
    public void validateUserData(RequestUserDto requestUserDto) throws IllegalArgumentException{
        if (!EmailValidator.getInstance().isValid(requestUserDto.getEmail())) {
            throw new IllegalArgumentException("Email is not valid!");
        }

        if (!this.isValidUserFirstName(requestUserDto.getFirstName())
                && !this.isValidUserLastName(requestUserDto.getLastName())) {
            throw new IllegalArgumentException("Firstname and lastname fields cannot be empty!");
        }

        if (!this.isValidUserFirstName(requestUserDto.getFirstName())) {
            throw new IllegalArgumentException("Firstname field cannot be empty!");
        }

        if (!this.isValidUserLastName(requestUserDto.getLastName())) {
            throw new IllegalArgumentException("Lastname field cannot be empty!");
        }

        if (!this.isValidUserPassword(requestUserDto.getPassword())){
            throw new IllegalArgumentException("Password is not valid!");
        }
    }

    public boolean isValidUserFirstName(String firstName) throws IllegalArgumentException{
        if (firstName != null) {
            return !firstName.isBlank();
        } else {
            throw new IllegalArgumentException("Firstname cannot be null!");
        }
    }

    public boolean isValidUserLastName(String lastName) throws IllegalArgumentException{
        if (lastName != null) {
            return !lastName.isBlank();
        } else {
            throw new IllegalArgumentException("Lastname cannot be null!");
        }
    }

    public boolean isValidUserPassword(String password){
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$");
    }
}
