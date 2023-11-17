package com.familywebshop.stylet.service;

import com.familywebshop.stylet.dto.RequestUserDTO;
import com.familywebshop.stylet.model.Role;

public interface UserService {
    void signUp(RequestUserDTO requestUserDTO);
    Role getRole(String username);
}
