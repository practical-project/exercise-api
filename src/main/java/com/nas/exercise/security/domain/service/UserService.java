package com.nas.exercise.security.domain.service;

import com.nas.exercise.security.domain.model.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<User> getAll(Pageable pageable);
    User getById(Integer userId);
    User updateById(Integer userId, String newUsername);
    Boolean deleteById(Integer userId);
}
