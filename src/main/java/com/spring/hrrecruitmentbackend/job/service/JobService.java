package com.spring.hrrecruitmentbackend.job.service;

import com.spring.hrrecruitmentbackend.job.model.entity.Job;
import com.spring.hrrecruitmentbackend.job.model.request.JobRequest;
import com.spring.hrrecruitmentbackend.job.model.response.JobResponse;
import com.spring.hrrecruitmentbackend.job.repository.JobRepository;
import com.spring.hrrecruitmentbackend.utils.JobMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.spring.hrrecruitmentbackend.job.exceptions.JobNotFoundException;

import java.util.List;
import java.util.Optional;

@Getter
@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public JobResponse getJobById(Long id) {
        return jobRepository.findById(id).map(JobMapper::toJobResponse)
                .orElseThrow(JobNotFoundException::new);
    }

    public Job updateJob(Job job) {
        jobRepository.save(job);
        return job;
    }

    public void addJob(JobRequest jobRequest) {
        Job job = JobMapper.toJobEntity(jobRequest);
        jobRepository.save(job);
    }

    public void deleteJobById(Long id) {
        jobRepository.deleteById(id);
    }

    public Optional<Job> getJobByTitle(String title) {
    return jobRepository.findByTitle(title);
    }

}
