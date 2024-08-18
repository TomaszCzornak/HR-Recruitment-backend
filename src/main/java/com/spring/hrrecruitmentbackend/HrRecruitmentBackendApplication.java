package com.spring.hrrecruitmentbackend;

import com.spring.hrrecruitmentbackend.gate.WebSocketConfig;
import com.spring.hrrecruitmentbackend.infrastructure.Seeds;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@RequiredArgsConstructor
@SpringBootApplication
@Import(WebSocketConfig.class)
public class HrRecruitmentBackendApplication implements CommandLineRunner {

    private final Seeds seeds;

    public static void main(String[] args) {
        SpringApplication.run(HrRecruitmentBackendApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        seeds.generateUsers();
        seeds.generateSkills();
        seeds.generateJobs();
        seeds.generateCandidates();
        seeds.generateRecruitments();
    }
}
