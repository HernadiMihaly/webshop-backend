package com.familywebshop.stylet.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
public class ResponseUserDto {

    private String email;

    private String firstName;

    private String lastName;

}
