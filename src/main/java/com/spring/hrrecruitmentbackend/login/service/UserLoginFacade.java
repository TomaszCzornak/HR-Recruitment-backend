package com.spring.hrrecruitmentbackend.login.service;

import com.spring.hrrecruitmentbackend.login.model.LoginRequest;
import com.spring.hrrecruitmentbackend.user.model.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public interface UserLoginFacade {

    ResponseEntity<User> loginUser(LoginRequest loginRequest);
}
