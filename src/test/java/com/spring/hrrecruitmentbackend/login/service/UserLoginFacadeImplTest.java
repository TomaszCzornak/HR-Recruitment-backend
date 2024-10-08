package com.spring.hrrecruitmentbackend.login.service;

import com.spring.hrrecruitmentbackend.Utils.UserStub;
import com.spring.hrrecruitmentbackend.login.model.LoginRequest;
import com.spring.hrrecruitmentbackend.security.JwtService;
import com.spring.hrrecruitmentbackend.user.model.entity.User;
import com.spring.hrrecruitmentbackend.user.service.UsersService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;


import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class UserLoginFacadeImplTest {
    @InjectMocks
    private UserLoginFacadeImpl userLoginFacadeImpl;
    @Mock
    private UsersService usersServiceMock;
    @Mock
    private JwtService jwtServiceMock;
    @Mock
    private AuthenticationManager authenticationMock;

    @Test
    void loginUser() {
        //given
        int exp = 1000 * 60 * 60 * 24 * 3;
        LoginRequest loginRequest = UserStub.createLoginRequest();
        User user = UserStub.createUser();
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        Authentication authenticated = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword(), new ArrayList<>());        given(usersServiceMock.getActivatedUser(loginRequest.getEmail())).willReturn(Optional.of(user));
        given(authenticationMock.authenticate(authentication)).willReturn(authenticated);
        given(jwtServiceMock.generateToken(user.getEmail(), exp)).willReturn("token");
        HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.add("Authorization", "Bearer token");
        //when
        ResponseEntity<?> responseEntity = userLoginFacadeImpl.loginUser(loginRequest);
        //then
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getHeaders(), headers);

    }
}