package com.familywebshop.stylet.service.impl;

import com.familywebshop.stylet.model.ConfirmationToken;
import com.familywebshop.stylet.repository.ConfirmationTokenRepository;
import com.familywebshop.stylet.service.ConfirmationTokenService;
import com.familywebshop.stylet.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public void saveConfirmationToken(ConfirmationToken confirmationToken){
        confirmationTokenRepository.save(confirmationToken);
    }

    public int setConfirmedAt(String token) {
        return confirmationTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }

    @Override
    public ConfirmationToken getToken(String token) throws IllegalStateException {
        return confirmationTokenRepository.findByToken(token).orElseThrow(() ->
                new IllegalStateException("token not found"));
    }
}
