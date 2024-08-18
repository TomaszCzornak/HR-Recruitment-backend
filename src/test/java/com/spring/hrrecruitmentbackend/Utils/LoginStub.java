package com.spring.hrrecruitmentbackend.Utils;

import com.spring.hrrecruitmentbackend.login.model.LoginRequest;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LoginStub {

    public static LoginRequest createLoginRequest() {
        return LoginRequest.builder()
                .email("registration@mail.com")
                .password("!CiężkieHasło444")
                .build();
    }


}
