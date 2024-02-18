package com.familywebshop.stylet.service;

import com.familywebshop.stylet.dto.RequestUserDto;
import com.familywebshop.stylet.model.Role;
import com.familywebshop.stylet.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserService {

    User signUp(RequestUserDto requestUserDTO);
    Role getRole(String username);

    int enableUser(String email);
}
