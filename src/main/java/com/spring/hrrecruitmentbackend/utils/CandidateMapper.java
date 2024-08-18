package com.spring.hrrecruitmentbackend.utils;

import com.spring.hrrecruitmentbackend.candidate.model.dto.CandidateDto;
import com.spring.hrrecruitmentbackend.candidate.model.entity.Candidate;
import com.spring.hrrecruitmentbackend.candidate.model.request.CandidateRequest;
import com.spring.hrrecruitmentbackend.candidate.model.response.CandidateResponse;
import com.spring.hrrecruitmentbackend.user.model.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;


import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CandidateMapper {

    public static CandidateDto toCandidateDto(CandidateRequest candidateRequest, User userPerCandidate) {
            return CandidateDto.builder()
                    .createdBy(UserMapper.toUserDto(userPerCandidate))
                    .email(candidateRequest.getEmail())
                    .build();

    }

    public static Candidate toCandidateEntity(CandidateDto candidateDto) {
        return Candidate.builder()
                .email(candidateDto.getEmail())
                .createdBy(User.builder()
                        .email(candidateDto.getCreatedBy().getEmail())
                        .build())
                .build();
    }

    public static CandidateResponse toCandidateResponse(Candidate candidate) {
        return CandidateResponse.builder()
                .id(candidate.getId())
                .createdBy(UserMapper.toUserDtoRecruitment(candidate.getCreatedBy()))
                .email(candidate.getEmail())
                .build();
    }


    public static List<CandidateResponse> toCandidateResponseList(List<Candidate> candidateList) {
        return candidateList.stream()
                .map(CandidateMapper::toCandidateResponse)
                .toList();
    }


    public static Candidate toCandidateEntity(CandidateRequest candidateRequest) {
        return Candidate.builder()
                .email(candidateRequest.getEmail())
                .createdBy(candidateRequest.getCreatedBy())
                .build();
    }

    public static CandidateDto toCandidateDto(Candidate candidate) {
        return CandidateDto.builder()
                .id(candidate.getId())
                .createdBy(UserMapper.toUserDto(candidate.getCreatedBy()))
                .email(candidate.getEmail())
                .build();
    }

}