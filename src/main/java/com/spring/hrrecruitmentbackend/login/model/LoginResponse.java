package com.spring.hrrecruitmentbackend.login.model;

import com.spring.hrrecruitmentbackend.user.model.dto.UserDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

    private String accessToken;
    private UserDto userDto;

}
