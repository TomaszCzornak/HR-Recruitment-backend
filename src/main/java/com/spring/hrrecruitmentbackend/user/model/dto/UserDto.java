package com.spring.hrrecruitmentbackend.user.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

    private Long id;
    private String createdAt;
    private String updatedAt;
    private String firstName;
    private String lastName;
    private String email;

}
