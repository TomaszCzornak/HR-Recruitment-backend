package com.spring.hrrecruitmentbackend.registration.service;

import com.spring.hrrecruitmentbackend.Utils.UserStub;
import com.spring.hrrecruitmentbackend.email.EmailService;
import com.spring.hrrecruitmentbackend.registration.model.entity.ResetOperations;
import com.spring.hrrecruitmentbackend.registration.model.request.ChangePasswordData;
import com.spring.hrrecruitmentbackend.registration.repository.RegistrationRepository;
import com.spring.hrrecruitmentbackend.registration.repository.ResetOperationsRepository;
import com.spring.hrrecruitmentbackend.security.Role;
import com.spring.hrrecruitmentbackend.user.model.entity.User;
import com.spring.hrrecruitmentbackend.user.repository.UserRepository;
import com.spring.hrrecruitmentbackend.user.service.UsersService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {
    @Mock
    private RegistrationRepository registrationRepository;
    @Mock
    private UsersService usersService;
    @Mock
    private ResetOperationService resetOperationService;
    @Mock
    private EmailService emailService;
    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private ResetOperationsRepository resetOperationsRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private RegistrationService registrationService;

    @Test
    void register_ShouldSaveNewUserToDb() {
        User user = UserStub.createUser();
        String originalPassword = user.getPassword();
        String encodedPassword = passwordEncoder.encode(originalPassword);
        given(passwordEncoder.encode(originalPassword)).willReturn(encodedPassword);
        given(registrationRepository.save(user)).willReturn(user);
        //when
        User userRegistered = registrationService.register(user);
        //then
        assertNotNull(userRegistered);
        assertNotEquals(userRegistered.getPassword(),originalPassword);
        assertEquals(userRegistered.getPassword(), encodedPassword);

    }

    @Test
    void activateUser_ShouldSetLockAndEnabledAndRole() {
        User user = UserStub.createUserBeforeRegistration();
        String uid = "123456";
        given(usersService.getUserByUuid(uid)).willReturn(Optional.of(user));
        //when
        registrationService.activateUser(uid);
        //then
        assertTrue(user.isEnabled());
        assertFalse(user.isLock());
        assertEquals(user.getRole(), Role.USER);
    }

    @Test
    void recoveryPassword_ShouldSendEmailWithFormToChangePass() throws IOException {
        //given
        User user = UserStub.createUser();
        String email = "test@example.com";
        given(usersService.getUsersByEmail(email)).willReturn(Optional.of(user));
        //when
        registrationService.recoveryPassword(email);
        //then
        verify(emailService, times(1)).sendMail(user, false);
    }

    @Test
    void resetPassword_ShouldChangeExistingUserPassword() throws IOException {
        //given
        User user = UserStub.createUser();
        UUID fixedUUID = UUID.fromString("44e54e27-e32a-4289-85fe-d528c883a94c");
        ResetOperations resetOperations = new ResetOperations(fixedUUID, user, user.getCreatedAt(), fixedUUID.toString());
        ChangePasswordData changePasswordData = new ChangePasswordData("Duperele89!", fixedUUID.toString());
        given(usersService.getUserByUuid(resetOperations.getUser().getUuid())).willReturn(Optional.of(user));
        given(resetOperationsRepository.findByUuid(changePasswordData.getUuid())).willReturn(Optional.of(resetOperations));
        //when
        registrationService.resetPassword(changePasswordData);
        //then
        verify(resetOperationService, times(1)).endOperation(fixedUUID.toString());


    }
}