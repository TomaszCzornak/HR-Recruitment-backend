package com.spring.hrrecruitmentbackend.registration.service;

import com.spring.hrrecruitmentbackend.email.EmailService;
import com.spring.hrrecruitmentbackend.registration.model.entity.ResetOperations;
import com.spring.hrrecruitmentbackend.registration.model.request.ChangePasswordData;
import com.spring.hrrecruitmentbackend.registration.repository.RegistrationRepository;
import com.spring.hrrecruitmentbackend.registration.repository.ResetOperationsRepository;
import com.spring.hrrecruitmentbackend.security.Role;
import com.spring.hrrecruitmentbackend.user.exceptions.UserNotFoundException;
import com.spring.hrrecruitmentbackend.user.model.entity.User;
import com.spring.hrrecruitmentbackend.user.service.UsersService;
import lombok.RequiredArgsConstructor;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;




import java.io.IOException;


@Service
@RequiredArgsConstructor
public class RegistrationService {


    private final RegistrationRepository registrationRepository;
    private final UsersService usersService;
    private final ResetOperationService resetOperationService;
    private final EmailService emailService;
    private final ResetOperationsRepository resetOperationsRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return registrationRepository.save(user);
    }

    public void activateUser(String uid) throws UserNotFoundException {
        User user = usersService.getUserByUuid(uid).orElse(null);
        if (user != null) {
            user.setLock(false);
            user.setEnabled(true);
            user.setRole(Role.USER);
            usersService.saveUser(user);
            return;
        }
        throw new UserNotFoundException();
    }

    public void recoveryPassword(String email) throws UserNotFoundException, IOException {
        User user = usersService.getUsersByEmail(email).orElse(null);
        if (user != null) {

            emailService.sendMail(user, false);
            return;
        }
        throw new UserNotFoundException();
    }

    public void resetPassword(ChangePasswordData changePasswordData) throws UserNotFoundException {
        ResetOperations resetOperations = resetOperationsRepository.findByUuid(changePasswordData.getUuid()).orElse(null);
        if (resetOperations != null) {
            User user = usersService.getUserByUuid(resetOperations.getUser().getUuid()).orElse(null);

            if (user != null) {
                user.setPassword(changePasswordData.getPassword());
                register(user);
                resetOperationService.endOperation(resetOperations.getUuid());
                return;
            }
        }
        throw new UserNotFoundException();
    }

}