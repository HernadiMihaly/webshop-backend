package com.familywebshop.stylet.service.user;

import com.familywebshop.stylet.model.user.ConfirmationToken;

public interface ConfirmationTokenService {

    void saveConfirmationToken(ConfirmationToken confirmationToken);

    int setConfirmedAt(String token);

    ConfirmationToken getToken(String token);
}