package com.familywebshop.stylet.dto;

import com.familywebshop.stylet.model.Role;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
public class UserDTO {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private Role role;
}
