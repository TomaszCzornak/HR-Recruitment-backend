package com.spring.hrrecruitmentbackend.candidate.model.request;

import com.spring.hrrecruitmentbackend.user.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CandidateRequest {

    private String email;
    private User createdBy;
}
