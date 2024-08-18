package com.spring.hrrecruitmentbackend.Utils;

import com.spring.hrrecruitmentbackend.candidate.model.dto.CandidateDto;
import com.spring.hrrecruitmentbackend.recruitment.entity.Recruitment;
import com.spring.hrrecruitmentbackend.recruitment.model.request.RecruitmentRequest;
import com.spring.hrrecruitmentbackend.recruitment.model.response.RecruitmentResponse;
import com.spring.hrrecruitmentbackend.utils.JobMapper;
import com.spring.hrrecruitmentbackend.utils.RecruitmentMapper;
import com.spring.hrrecruitmentbackend.utils.UserMapper;
import lombok.experimental.UtilityClass;


import java.util.List;

@UtilityClass
public class RecruitmentStub {

    public static Recruitment createRecruitment() {
        return Recruitment.builder()
                .job(JobStub.createJob())
                .candidate(CandidateStub.createCandidate())
                .build();
    }

    public static List<Recruitment> createRecruitmentList() {
        return List.of(createRecruitment(), createRecruitment());
    }

    public static RecruitmentRequest createRecruitmentRequest() {
        return RecruitmentRequest.builder()
                .candidateDto(CandidateDto.builder().email("kandydat@candidaterequest.com")
                        .createdBy(UserMapper.toUserDto(UserStub.createUser())).build())
                .jobDto(JobMapper.toJobDto(JobStub.createJob()))
                .build();
    }

    public static List<RecruitmentResponse> createRecruitmentResponseList() {
        return RecruitmentMapper.toRecruitmentsResponses(createRecruitmentList());
    }

    public static RecruitmentResponse createRecruitmentResponse() {
        return RecruitmentMapper.toRecruitmentResponse(createRecruitment());
    }
}
