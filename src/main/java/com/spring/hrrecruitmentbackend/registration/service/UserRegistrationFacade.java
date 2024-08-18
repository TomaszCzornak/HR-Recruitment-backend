package com.spring.hrrecruitmentbackend.registration.service;

import com.spring.hrrecruitmentbackend.registration.model.request.RegistrationRequest;
import com.spring.hrrecruitmentbackend.user.model.response.UserResponse;

import java.io.IOException;

public interface UserRegistrationFacade {

    UserResponse registerUser(RegistrationRequest registrationRequest) throws IOException;
}
