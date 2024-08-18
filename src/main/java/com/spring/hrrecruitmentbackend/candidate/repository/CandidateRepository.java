package com.spring.hrrecruitmentbackend.candidate.repository;


import com.spring.hrrecruitmentbackend.candidate.model.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    Candidate findCandidateByEmail(String email);
}
