package com.spring.hrrecruitmentbackend.candidate.model.dto;

import com.spring.hrrecruitmentbackend.recruitment.model.response.RecruitmentResponse;
import com.spring.hrrecruitmentbackend.user.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CandidateDto {


    private Long id;

    private String email;

    private UserDto createdBy;
    private List<RecruitmentResponse> recruitmentResponses;
}
