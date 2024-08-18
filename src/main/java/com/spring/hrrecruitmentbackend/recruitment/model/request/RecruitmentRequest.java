package com.spring.hrrecruitmentbackend.recruitment.model.request;

import com.spring.hrrecruitmentbackend.candidate.model.dto.CandidateDto;
import com.spring.hrrecruitmentbackend.job.model.dto.JobDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentRequest {

    private JobDto jobDto;
    private CandidateDto candidateDto;
}
