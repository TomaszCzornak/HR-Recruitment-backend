package com.spring.hrrecruitmentbackend.recruitment.model.dto;

import com.spring.hrrecruitmentbackend.candidate.model.dto.CandidateDto;
import com.spring.hrrecruitmentbackend.job.model.dto.JobDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentDto {

    private JobDto jobDto;
    private CandidateDto candidateDto;

}
