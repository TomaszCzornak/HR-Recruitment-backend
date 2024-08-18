package com.spring.hrrecruitmentbackend.user.repository;

import com.spring.hrrecruitmentbackend.email.EmailService;
import com.spring.hrrecruitmentbackend.registration.model.request.RegistrationRequest;
import com.spring.hrrecruitmentbackend.registration.service.RegistrationService;
import com.spring.hrrecruitmentbackend.registration.service.UserRegistrationFacade;
import com.spring.hrrecruitmentbackend.user.exceptions.UserAlreadyExistsException;
import com.spring.hrrecruitmentbackend.user.model.entity.User;
import com.spring.hrrecruitmentbackend.user.model.response.UserResponse;
import com.spring.hrrecruitmentbackend.user.service.UsersService;
import com.spring.hrrecruitmentbackend.utils.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRegistrationImpl implements UserRegistrationFacade {

    private final UsersService usersService;
    private final RegistrationService registrationService;
    private final EmailService emailService;

    @Override
    public UserResponse registerUser(RegistrationRequest registrationRequest) throws IOException {
        Optional<User> existingUser = usersService.getUsersByEmail(registrationRequest.getEmail());
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException();
        }
        User user = UserMapper.toUser(registrationRequest);

        User userSaved = registrationService.register(user);
        emailService.sendMail(user, true);

        return UserMapper.toUserResponse(userSaved);

    }
}
