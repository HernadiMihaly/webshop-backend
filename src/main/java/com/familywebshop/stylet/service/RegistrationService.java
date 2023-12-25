package com.familywebshop.stylet.service;

import com.familywebshop.stylet.dto.RequestUserDto;
import com.familywebshop.stylet.dto.SubscribedUserDto;

public interface RegistrationService {

    void register(RequestUserDto requestUserDTO);

    void subscribe(SubscribedUserDto subscribedUserDto);
}
