package com.spring.hrrecruitmentbackend.utils;

import com.spring.hrrecruitmentbackend.recruitment.entity.Recruitment;
import com.spring.hrrecruitmentbackend.recruitment.model.request.RecruitmentRequest;
import com.spring.hrrecruitmentbackend.recruitment.model.response.RecruitmentResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;


import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RecruitmentMapper {


    public static Recruitment toRecruitmentEntity(RecruitmentRequest recruitmentRequest) {
        return Recruitment.builder()
                .id(recruitmentRequest.getCandidateDto().getId())
                .job(JobMapper.toJobEntity(recruitmentRequest.getJobDto()))
                .candidate(CandidateMapper.toCandidateEntity(recruitmentRequest.getCandidateDto()))
                .build();
    }

    public static RecruitmentResponse toRecruitmentResponse(Recruitment recruitment) {
        return RecruitmentResponse.builder()
                .jobDto(JobMapper.toJobDto(recruitment.getJob()))
                .candidateDto(CandidateMapper.toCandidateDto(recruitment.getCandidate()))
                .build();
    }

    public static List<RecruitmentResponse> toRecruitmentsResponses(List<Recruitment> recruitments) {
        return recruitments.stream()
                .map(RecruitmentMapper::toRecruitmentResponse)
                .toList();
    }
}
