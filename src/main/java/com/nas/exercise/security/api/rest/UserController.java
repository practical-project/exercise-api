package com.nas.exercise.security.api.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nas.exercise.security.domain.service.UserService;
import com.nas.exercise.security.mapping.UserMapper;
import com.nas.exercise.security.resource.request.UpdateUserRequest;
import com.nas.exercise.security.resource.response.UserResponse;

@RestController
@RequestMapping(value = "/api/v1/users", produces = "application/json")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return userMapper.toUserResponsePage(userService.getAll(pageable));
    }

    @GetMapping("/me")
    public UserResponse getMyUser() {
        return userMapper.toUserResponse(userService.getById(1));
    }

    @PutMapping("/me")
    public UserResponse updateMyUser(@RequestBody UpdateUserRequest request) {
        return userMapper.toUserResponse(userService.updateById(1, request.getUsername()));
    }

    @DeleteMapping("/me")
    public Boolean deleteMyUser() {
        return userService.deleteById(1);
    }
}
