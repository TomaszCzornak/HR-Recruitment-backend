package com.spring.hrrecruitmentbackend.login.api;

import com.spring.hrrecruitmentbackend.login.model.LoginRequest;
import com.spring.hrrecruitmentbackend.login.service.UserLoginFacade;
import com.spring.hrrecruitmentbackend.user.model.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@Slf4j
public class LoginController {

    private final UserLoginFacade userLoginFacade;

    @PostMapping("/login")
    @Operation(summary = "Login User", description = "This endpoint is for user login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully logged in"),
            @ApiResponse(responseCode = "400", description = "Bad request due to validation failure")
    })
    public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest) {

        return userLoginFacade.loginUser(loginRequest);

    }

}
