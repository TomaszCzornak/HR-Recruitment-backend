package com.spring.hrrecruitmentbackend.login.service;

import com.spring.hrrecruitmentbackend.login.model.LoginRequest;
import com.spring.hrrecruitmentbackend.security.JwtService;
import com.spring.hrrecruitmentbackend.user.exceptions.UserNotFoundException;
import com.spring.hrrecruitmentbackend.user.model.entity.User;
import com.spring.hrrecruitmentbackend.user.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserLoginFacadeImpl implements UserLoginFacade {

    private final UsersService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public static final String BEARER = "Bearer ";

    @Override
    public ResponseEntity<User> loginUser(LoginRequest loginRequest) {
        Optional<User> user = userService.getActivatedUser(loginRequest.getEmail());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        if (user.isPresent() && authentication.isAuthenticated()) {
            int exp = 1000 * 60 * 60 * 24 * 3;
            String token = generateToken(user.get().getEmail(), exp);
            HttpHeaders httpHeaders = new org.springframework.http.HttpHeaders();
            httpHeaders.add("Authorization", BEARER + token);
            return new ResponseEntity<>(user.get(), httpHeaders, HttpStatus.OK);
           }

       throw new UserNotFoundException();
   }

   private String generateToken(String email, int exp) {
       return jwtService.generateToken(email, exp);
   }
}