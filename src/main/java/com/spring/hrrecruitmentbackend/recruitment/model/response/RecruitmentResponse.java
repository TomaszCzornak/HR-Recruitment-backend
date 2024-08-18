package com.spring.hrrecruitmentbackend.recruitment.model.response;

import com.spring.hrrecruitmentbackend.candidate.model.dto.CandidateDto;
import com.spring.hrrecruitmentbackend.job.model.dto.JobDto;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Getter
public class RecruitmentResponse {

    private JobDto jobDto;
    private CandidateDto candidateDto;
}
