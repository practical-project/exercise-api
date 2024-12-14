package com.nas.exercise.security.mapping;

import com.nas.exercise.security.domain.model.User;
import com.nas.exercise.security.resource.response.UserResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .role(user.getRole().name())
                .build();
    }

    public Page<UserResponse> toUserResponsePage(Page<User> usersPage) {
        return usersPage.map(this::toUserResponse);
    }
}
