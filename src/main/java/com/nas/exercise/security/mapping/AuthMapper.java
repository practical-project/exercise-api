package com.nas.exercise.security.mapping;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.nas.exercise.security.domain.model.Role;
import com.nas.exercise.security.domain.model.User;
import com.nas.exercise.security.domain.service.JwtService;
import com.nas.exercise.security.resource.request.RegisterRequest;
import com.nas.exercise.security.resource.response.AuthResponse;
import com.nas.exercise.security.resource.response.UserResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthMapper {
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse toAuthResponse(User user) {
        String token = jwtService.getToken(user);
        Date expirationTokenDate = jwtService.getExpiration(token);
        LocalDateTime expirationToken = expirationTokenDate.toInstant().atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        return AuthResponse.builder()
                .accessToken(token)
                .tokenType("bearer")
                .expiresIn(expirationToken)
                .userResponse(userMapper.toUserResponse(user))
                .build();
    }

    public User toModel(RegisterRequest request) {
        return User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .hashedPassword(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
    }
}
