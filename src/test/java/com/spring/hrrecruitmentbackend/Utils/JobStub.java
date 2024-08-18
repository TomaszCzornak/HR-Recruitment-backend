package com.spring.hrrecruitmentbackend.Utils;

import com.spring.hrrecruitmentbackend.job.model.entity.Currency;
import com.spring.hrrecruitmentbackend.job.model.entity.Job;
import com.spring.hrrecruitmentbackend.job.model.request.JobRequest;
import com.spring.hrrecruitmentbackend.job.model.response.JobResponse;
import com.spring.hrrecruitmentbackend.skills.entity.Skill;
import lombok.experimental.UtilityClass;


import java.util.List;

@UtilityClass
public class JobStub {

    public static List<Job> createJobs() {
        return List.of(createJob(), createJob());
    }

    public static Job createJob() {
        return Job.builder()
                .id(1L)
                .title("Java Developer")
                .city("Kraków")
                .salary(20000L)
                .currency(Currency.PLN)
                .skills(List.of(Skill.builder().name("Back-end").build(), Skill.builder().name("SQL").build()))
                .build();
    }

    public static JobRequest createJobRequest() {
        return JobRequest.builder()
                .title("Java Developer")
                .city("Kraków")
                .salary(20000L)
                .currency(Currency.PLN)
                .skills(List.of(Skill.builder().name("Back-end").build(), Skill.builder().name("SQL").build()))
                .build();
    }

    public static JobResponse createJobResponse() {
        return JobResponse.builder()
                .id(1L)
                .title("Java Developer")
                .city("Kraków")
                .salary(20000L)
                .currency(Currency.PLN)
                .skills(List.of(Skill.builder().name("Back-end").build(), Skill.builder().name("SQL").build()))
                .build();
    }
}
