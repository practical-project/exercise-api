package com.nas.exercise.security.service;

import com.nas.exercise.security.domain.model.User;
import com.nas.exercise.security.domain.persistence.UserRepository;
import com.nas.exercise.security.domain.service.UserService;
import com.nas.exercise.core.data.ValidatorUtil;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:email.properties")
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Page<User> getAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
    
    @Override
    public User getById(Integer userId) {
        // try {
        //     return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // } catch (AuthenticationException e) {
        //     throw new AuthenticationException("Not authenticated") {};
        // }
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Override
    @Transactional
    public User updateById(Integer userId, String newUsername) {

        // String userEmail;
        // try {
        //     userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        // } catch (AuthenticationException e) {
        //     throw new AuthenticationException("Not authenticated") {};
        // }

        // if (userRepository.updateUserByEmail(userEmail, request.getFirstName(), request.getLastName()) == 0) {
        //     throw new EntityNotFoundException("User not found");
        // }

        User userToUpdate = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        userToUpdate.setUsername(newUsername);
        
        return userRepository.save(userToUpdate);
    }

    @Override
    @Transactional
    public Boolean deleteById(Integer userId) {
        // String userEmail;
        // try {
        //     userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        // } catch (AuthenticationException e) {
        //     throw new AuthenticationException("Not authenticated") {};
        // }

        // if (userRepository.deleteUserByEmail(userEmail) == 0) {
        //     throw new EntityNotFoundException("User not found");
        // }

        User userToDelete = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        userRepository.delete(userToDelete);
        return true;
    }
}
