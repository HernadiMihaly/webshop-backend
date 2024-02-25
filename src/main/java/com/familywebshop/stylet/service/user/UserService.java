package com.familywebshop.stylet.service.user;

import com.familywebshop.stylet.dto.user.RequestUserDto;
import com.familywebshop.stylet.model.user.Role;
import com.familywebshop.stylet.model.user.User;

public interface UserService {

    User signUp(RequestUserDto requestUserDTO);
    Role getRole(String username);

    int enableUser(String email);
}
