package com.spring.hrrecruitmentbackend.recruitment.entity;

import com.spring.hrrecruitmentbackend.candidate.model.entity.Candidate;
import com.spring.hrrecruitmentbackend.job.model.entity.Job;
import jakarta.persistence.*;
import lombok.*;


@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class Recruitment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="job_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Job job;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Candidate candidate;

}
