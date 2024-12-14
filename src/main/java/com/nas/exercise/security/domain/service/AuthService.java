package com.nas.exercise.security.domain.service;

import com.nas.exercise.security.domain.model.User;

public interface AuthService {
    User authenticate(String email, String password);
    User register(User user);
    Boolean verifyEmail(String verificationUuid);
    Boolean sendEmailToResetPassword(String email);
    Boolean ResetPassword(String verificationUuid, String newHashedPassword);
}

