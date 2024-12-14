
package com.nas.exercise.security.api.rest;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nas.exercise.security.domain.service.AuthService;
import com.nas.exercise.security.mapping.AuthMapper;
import com.nas.exercise.security.resource.request.CodeVerificationRequest;
import com.nas.exercise.security.resource.request.EmailVerificationRequest;
import com.nas.exercise.security.resource.request.LoginRequest;
import com.nas.exercise.security.resource.request.PasswordResetRequestByEmail;
import com.nas.exercise.security.resource.request.RegisterRequest;
import com.nas.exercise.security.resource.request.ResetPasswordRequest;
import com.nas.exercise.security.resource.request.UniqidRequest;
import com.nas.exercise.security.resource.response.AuthResponse;

@RestController
@RequestMapping(value = "/api/v1/auth", produces = "application/json")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final AuthMapper authMapper;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authMapper.toAuthResponse(authService.authenticate(request.getEmail(), request.getPassword())));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authMapper.toAuthResponse(authService.register(authMapper.toModel(request))));
    }

    @PostMapping("/verify-email")
    public ResponseEntity<Boolean> verifyEmail(@RequestBody EmailVerificationRequest request){
        return ResponseEntity.ok(authService.verifyEmail(request.getVerificationUuid()));
    }

    @PostMapping("/forgot-password/send-code")
    public ResponseEntity<Boolean> sendResetPasswordCode(@RequestBody PasswordResetRequestByEmail request){
        return ResponseEntity.ok(authService.sendEmailToResetPassword(request.getEmail()));
    }

    @PostMapping("/forgot-password/reset-password")
    public ResponseEntity<Boolean> ResetPassword(@RequestBody ResetPasswordRequest request){
        return ResponseEntity.ok(authService.ResetPassword(request.getVerificationUuid(), request.getPassword()));
    }
}
