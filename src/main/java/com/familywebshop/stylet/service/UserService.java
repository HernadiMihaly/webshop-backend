package com.familywebshop.stylet.service;

import com.familywebshop.stylet.dto.UserDTO;
import com.familywebshop.stylet.model.Role;

public interface UserService {
    void signUp(UserDTO userDTO);
    Role getRole(String username);
}
