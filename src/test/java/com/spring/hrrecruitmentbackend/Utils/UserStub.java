package com.spring.hrrecruitmentbackend.Utils;

import com.spring.hrrecruitmentbackend.login.model.LoginRequest;
import com.spring.hrrecruitmentbackend.security.Role;
import com.spring.hrrecruitmentbackend.user.model.entity.User;
import com.spring.hrrecruitmentbackend.user.model.response.UserResponse;
import com.spring.hrrecruitmentbackend.utils.UserMapper;
import lombok.experimental.UtilityClass;


import java.util.UUID;

@UtilityClass
public class UserStub {

    public static LoginRequest createLoginRequest() {
        return LoginRequest.builder()
                .email("john.doe@mail.com")
                .password("Passwording85!")
                .build();
    }

    public static User createUser() {
        return User.builder()
                .id(1L)
                .uuid("1234-5678-9012-3456")
                .createdAt("2022-01-01")
                .updatedAt("2022-01-01")
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@mail.com")
                .password("Passwording85!")
                .isLock(false)
                .isEnabled(true)
                .role(Role.USER)
                .build();
    }

    public static User createUserBeforeRegistration() {
        return User.builder()
                .id(1L)
                .uuid(UUID.randomUUID().toString())
                .createdAt("2022-01-01")
                .updatedAt("2022-01-01")
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@mail.com")
                .password("Passwording85!")
                .isLock(true)
                .isEnabled(false)
                .role(Role.USER)
                .build();
    }

    public static LoginRequest createInvalidLoginRequest() {
        return LoginRequest.builder()
                .email("john.doe.wrong@mail.com")
                .password("Passwording85!")
                .build();
    }

    public static UserResponse createUserResponse() {
        return UserMapper.toUserResponse(createUser());
    }
}
