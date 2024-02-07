package com.familywebshop.stylet.service.impl;

import com.familywebshop.stylet.dto.AuthenticationResponse;
import com.familywebshop.stylet.dto.RequestUserDto;
import com.familywebshop.stylet.dto.SubscribedUserDto;
import com.familywebshop.stylet.model.SubscribedUser;
import com.familywebshop.stylet.model.User;
import com.familywebshop.stylet.repository.SubscribedUserRepository;
import com.familywebshop.stylet.service.JwtService;
import com.familywebshop.stylet.service.RegistrationService;
import com.familywebshop.stylet.service.UserService;
import com.familywebshop.stylet.util.ModelMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final UserService userService;

    private final SubscribedUserRepository subscribedUserRepository;

    private final JwtService jwtService;

    @Override
    public AuthenticationResponse register(RequestUserDto requestUserDTO) throws IllegalArgumentException{
            User user = userService.signUp(requestUserDTO);

            return AuthenticationResponse.builder()
                    .token(jwtService.generateToken(user))
                    .build();
    }

    @Override
    public void subscribe(SubscribedUserDto subscribedUserDto) throws IllegalStateException{
        if (EmailValidator.getInstance().isValid(subscribedUserDto.getEmail())) {
            subscribedUserRepository.save(
                    ModelMapper.getInstance()
                            .mapDtoToEntity(subscribedUserDto, SubscribedUser.class)
            );
        } else {
            throw new IllegalArgumentException("Email is not valid!");
        }
    }
}
