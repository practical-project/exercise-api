package com.nas.exercise.security.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.nas.exercise.security.domain.model.User;
import com.nas.exercise.security.domain.persistence.UserRepository;
import com.nas.exercise.security.domain.service.AuthService;
import com.nas.exercise.security.domain.service.EmailService;
import com.nas.exercise.core.data.ValidatorUtil;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:email.properties")
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final ValidatorUtil validatorUtil;

    @Value("${email.URL_TIENDA}")
    private String urlTienda;

    private final EmailService emailService; // Inyectar EmailService

    @Override
    public User authenticate(String email, String password) {

        User authenticatedUser;
        try {
            authenticatedUser = (User) authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)).getPrincipal();
        } catch (AuthenticationException e) {
            throw new AuthenticationException("Credenciales inválidas") {
            };
        }

        if (authenticatedUser.getEmailVerifiedAt() == null) {
            throw new AuthenticationException("Credenciales inválidas por verificacion") {
            };
        }
        return authenticatedUser;
    }

    @Override
    @Transactional
    public User register(User user) {
        user.setVerificationUuid(generateUUID());

        validatorUtil.validate(user);

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new DataIntegrityViolationException("Already existing user");
        }

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new DataIntegrityViolationException("Already existing user");
        }

        User savedUser = userRepository.save(user);
        String enlace = urlTienda + "/login?code=" + user.getVerificationUuid();
        emailService.sendEmailVerification(user.getEmail(), "verificacion", enlace);
        return savedUser;
    }

    @Override
    @Transactional
    public Boolean verifyEmail(String verificationUuid) {
        User userVerify = userRepository.findByVerificationUuid(verificationUuid)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (userVerify.getEmailVerifiedAt() == null) {
            throw new DataIntegrityViolationException("User not verified");
        }

        userVerify.setEmailVerifiedAt(LocalDateTime.now());
        userVerify.setVerificationUuid(generateUUID());
        return true;
    }

    @Override
    @Transactional
    public Boolean sendEmailToResetPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (user.getEmailVerifiedAt() == null) {
            throw new EntityNotFoundException("User not verified");
        }

        String enlace = urlTienda + "/reset-password?uuid=" + user.getVerificationUuid();
        emailService.sendPasswordReset(email, "verificacion", enlace);
        return true;
    }

    @Override
    @Transactional
    public Boolean ResetPassword(String verificationUuid, String newHashedPassword) {
        User user = userRepository.findByVerificationUuid(verificationUuid)
                .orElseThrow(() -> new EntityNotFoundException("invalid code"));

        if (user.getEmailVerifiedAt() == null) {
            throw new EntityNotFoundException("User not verified");
        }

        user.setHashedPassword(newHashedPassword);
        user.setVerificationUuid(generateUUID());
        return true;
    }

    private String generateUUID() {
        String uniqueUuid = UUID.randomUUID().toString();
        while (userRepository.findByVerificationUuid(uniqueUuid).isPresent());
        return uniqueUuid;
    }
}
