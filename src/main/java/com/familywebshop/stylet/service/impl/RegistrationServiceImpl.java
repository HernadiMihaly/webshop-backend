package com.familywebshop.stylet.service.impl;

import com.familywebshop.stylet.dto.RequestUserDto;
import com.familywebshop.stylet.dto.SubscribedUserDto;
import com.familywebshop.stylet.model.SubscribedUser;
import com.familywebshop.stylet.repository.SubscribedUserRepository;
import com.familywebshop.stylet.service.RegistrationService;
import com.familywebshop.stylet.service.UserService;
import com.familywebshop.stylet.util.ModelMapper;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final UserService userService;

    private final SubscribedUserRepository subscribedUserRepository;

    public RegistrationServiceImpl(UserService userService, SubscribedUserRepository subscribedUserRepository) {
        this.userService = userService;
        this.subscribedUserRepository = subscribedUserRepository;
    }

    @Override
    public void register(RequestUserDto requestUserDTO) {
        if (EmailValidator.getInstance().isValid(requestUserDTO.getEmail())) {
            userService.signUp(requestUserDTO);
        } else throw new IllegalStateException("Email is not valid!");
    }

    @Override
    public void subscribe(SubscribedUserDto subscribedUserDto) {
        if (EmailValidator.getInstance().isValid(subscribedUserDto.getEmail())) {
            subscribedUserRepository.save(
                    ModelMapper.getInstance()
                            .mapDtoToEntity(subscribedUserDto, SubscribedUser.class)
            );
        } else throw new IllegalStateException("Email is not valid!");
    }

}
