package com.familywebshop.stylet.service;

import com.familywebshop.stylet.dto.RequestUserDto;
import com.familywebshop.stylet.model.Role;
import com.familywebshop.stylet.model.User;

public interface UserService {

    User signUp(RequestUserDto requestUserDTO);
    Role getRole(String username);
}
