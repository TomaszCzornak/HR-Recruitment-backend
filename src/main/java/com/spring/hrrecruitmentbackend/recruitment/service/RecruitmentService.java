package com.spring.hrrecruitmentbackend.recruitment.service;

import com.spring.hrrecruitmentbackend.candidate.model.entity.Candidate;
import com.spring.hrrecruitmentbackend.candidate.service.CandidateService;
import com.spring.hrrecruitmentbackend.job.exceptions.JobNotFoundException;
import com.spring.hrrecruitmentbackend.job.model.entity.Job;
import com.spring.hrrecruitmentbackend.job.service.JobService;
import com.spring.hrrecruitmentbackend.recruitment.entity.Recruitment;
import com.spring.hrrecruitmentbackend.recruitment.model.request.RecruitmentRequest;
import com.spring.hrrecruitmentbackend.recruitment.model.response.RecruitmentResponse;
import com.spring.hrrecruitmentbackend.recruitment.repository.RecruitmentRepository;
import com.spring.hrrecruitmentbackend.user.exceptions.UserNotFoundException;
import com.spring.hrrecruitmentbackend.utils.RecruitmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecruitmentService {

    private final RecruitmentRepository recruitmentRepository;
    private final CandidateService candidateService;
    private final JobService jobService;

    public List<RecruitmentResponse> getAllRecruitments() {
        List<Recruitment> recruitments = recruitmentRepository.findAll();
        return RecruitmentMapper.toRecruitmentsResponses(recruitments);

    }

    public RecruitmentResponse getRecruitmentById(Long id) {
        return RecruitmentMapper.toRecruitmentResponse(recruitmentRepository.findById(id).orElseThrow(IllegalStateException::new));
    }

    public RecruitmentResponse updateRecruitment(RecruitmentRequest recruitmentRequest) {
        return RecruitmentMapper.toRecruitmentResponse(
                recruitmentRepository.save(RecruitmentMapper.toRecruitmentEntity(recruitmentRequest)));
    }

    public void deleteRecruitment(Long id) {
        recruitmentRepository.deleteById(id);
    }

    public RecruitmentResponse addRecruitment(RecruitmentRequest recruitmentRequest) {
        Candidate candidate = candidateService.getCandidateByEmail(recruitmentRequest.getCandidateDto().getEmail());
        if (candidate == null || candidate.getCreatedBy() == null) {
            throw new UserNotFoundException();
        }
        Optional<Job> job = jobService.getJobByTitle(recruitmentRequest.getJobDto().getTitle());
        if (job.isEmpty()) {
            throw new JobNotFoundException();
        }
        Recruitment recruitment = Recruitment.builder()
                .candidate(candidate)
                .job(job.get())
                .build();
        recruitmentRepository.save(recruitment);
        return RecruitmentMapper.toRecruitmentResponse(recruitment);
    }
}