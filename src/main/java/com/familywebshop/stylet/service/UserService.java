package com.familywebshop.stylet.service;

import com.familywebshop.stylet.dto.RequestUserDto;
import com.familywebshop.stylet.model.Role;

public interface UserService {
    void signUp(RequestUserDto requestUserDTO);
    Role getRole(String username);
}
