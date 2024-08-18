package com.spring.hrrecruitmentbackend.candidate.model.response;

import com.spring.hrrecruitmentbackend.user.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class CandidateResponse {

    private Long id;
    private String email;
    private UserDto createdBy;

}