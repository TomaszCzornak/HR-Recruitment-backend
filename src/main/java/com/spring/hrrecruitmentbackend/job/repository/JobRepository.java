package com.spring.hrrecruitmentbackend.job.repository;

import com.spring.hrrecruitmentbackend.job.model.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface JobRepository extends JpaRepository<Job, Long> {

    Optional<Job> findByTitle(String title);
}
