package com.spring.hrrecruitmentbackend.user.service;

import com.spring.hrrecruitmentbackend.user.model.entity.User;
import com.spring.hrrecruitmentbackend.user.model.response.UserResponse;
import com.spring.hrrecruitmentbackend.user.repository.UserRepository;
import com.spring.hrrecruitmentbackend.utils.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UserRepository userRepository;

    public Optional<User> getUsersByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
    public Optional<User> getUserByUuid(String uuid) {
        return userRepository.findUserByUuid(uuid);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findUserById(id);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public Optional<User> getActivatedUser(String email) {
        return userRepository.findUserByEmailAndLockAndEnabled(email);
    }

    public List<UserResponse> getAllUsers() {
        return UserMapper.toUserResponses(userRepository.findAll());
    }

    public User getLoggedUser() {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            username = userDetails.getUsername();
        } else {
            username = principal.toString();
        }
        return getUsersByEmail(username).orElseThrow(null);
    }

    public boolean getActivatedUserByEmail(String email) {
        Optional<User> foundUser = userRepository.findUserByEmailAndLockAndEnabled(email);
        return foundUser.isPresent();
    }

}
