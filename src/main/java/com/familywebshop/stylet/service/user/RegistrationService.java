package com.familywebshop.stylet.service.user;

import com.familywebshop.stylet.dto.user.AuthenticationResponse;
import com.familywebshop.stylet.dto.user.RequestUserDto;
import com.familywebshop.stylet.dto.user.SubscribedUserDto;

public interface RegistrationService {

    AuthenticationResponse register(RequestUserDto requestUserDTO);

    void subscribe(SubscribedUserDto subscribedUserDto);

    String confirmToken(String token);
}
