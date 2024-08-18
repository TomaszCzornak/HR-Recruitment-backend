package com.spring.hrrecruitmentbackend.Utils;

import com.spring.hrrecruitmentbackend.candidate.model.entity.Candidate;
import com.spring.hrrecruitmentbackend.candidate.model.request.CandidateRequest;
import com.spring.hrrecruitmentbackend.candidate.model.response.CandidateResponse;
import com.spring.hrrecruitmentbackend.user.model.dto.UserDto;
import com.spring.hrrecruitmentbackend.user.model.entity.User;
import com.spring.hrrecruitmentbackend.utils.UserMapper;
import lombok.experimental.UtilityClass;


import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class CandidateStub {

    public static List<Candidate> createCandidates() {
        List<Candidate> candidates = new ArrayList<>();
        candidates.add(createCandidate());
        candidates.add(createCandidate());
        return candidates;
    }

    public static Candidate createCandidate() {
        return Candidate.builder()
                .email("test@email.com")
                .createdBy(User.builder().email("loggedUserEmail@test.com").build())
                                .build();
    }

    public static CandidateResponse createCandidateResponse() {
        return CandidateResponse.builder()
                .id(1L)
                .email("test@email.com")
                .createdBy(UserMapper.toUserDto(UserStub.createUser()))
                                .build();
    }

    public static CandidateRequest createCandidateRequest() {
        return CandidateRequest.builder()
                .email("kandydat@candidaterequest.com")
                .createdBy(UserStub.createUser())
                        .build();
    }

    public static List<CandidateResponse> createCandidateResponseList() {
        return List.of(CandidateResponse.builder()
                .email("test@email.com")
                .createdBy(UserDto.builder().email("loggedUserEmail1@test.com").build())
                .build(),
                CandidateResponse.builder()
                        .email("test@email.com")
                        .createdBy(UserDto.builder().email("loggedUserEmail2@test.com").build())
                        .build());
    }
}


