package com.familywebshop.stylet.service;

import com.familywebshop.stylet.model.ConfirmationToken;

public interface ConfirmationTokenService {

    void saveConfirmationToken(ConfirmationToken confirmationToken);

    int setConfirmedAt(String token);

    ConfirmationToken getToken(String token);
}