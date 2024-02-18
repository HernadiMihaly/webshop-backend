package com.familywebshop.stylet.service;

import com.familywebshop.stylet.dto.AuthenticationResponse;
import com.familywebshop.stylet.dto.RequestUserDto;
import com.familywebshop.stylet.dto.SubscribedUserDto;

public interface RegistrationService {

    AuthenticationResponse register(RequestUserDto requestUserDTO);

    void subscribe(SubscribedUserDto subscribedUserDto);

    String confirmToken(String token);
}
