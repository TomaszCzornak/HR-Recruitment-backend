package com.spring.hrrecruitmentbackend.infrastructure;

import com.github.javafaker.Faker;
import com.spring.hrrecruitmentbackend.candidate.model.entity.Candidate;
import com.spring.hrrecruitmentbackend.candidate.repository.CandidateRepository;
import com.spring.hrrecruitmentbackend.job.model.entity.Currency;
import com.spring.hrrecruitmentbackend.job.model.entity.Job;
import com.spring.hrrecruitmentbackend.job.repository.JobRepository;
import com.spring.hrrecruitmentbackend.recruitment.entity.Recruitment;
import com.spring.hrrecruitmentbackend.recruitment.repository.RecruitmentRepository;
import com.spring.hrrecruitmentbackend.security.Role;
import com.spring.hrrecruitmentbackend.skills.entity.Skill;
import com.spring.hrrecruitmentbackend.skills.logic.SkillRepository;
import com.spring.hrrecruitmentbackend.user.model.entity.User;
import com.spring.hrrecruitmentbackend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;




import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Seeds {


    private static final Logger log = LoggerFactory.getLogger(Seeds.class);
    private final UserRepository userRepository;
    private final CandidateRepository candidateRepository;
    private final JobRepository jobRepository;
    private final SkillRepository skillRepository;
    private final RecruitmentRepository recruitmentRepository;
    private static final String specialChars = "@$!%*?&";

    Faker faker = new Faker();
    List<Candidate> candidateFake = new ArrayList<>();
    List<Job> jobsFake = new ArrayList<>();
    List<User> usersFake = new ArrayList<>();
    List<Recruitment> recruitmentList = new ArrayList<>();
    List<Skill> skillList = new ArrayList<>();

    public void generateUsers() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawPassword, encodedPassword;

        for (int i = 0; i < 30; i++) {
            do {
                rawPassword = faker.regexify("[A-Z]{1}")
                        + faker.regexify("[a-z]{1}")
                        + faker.regexify("[0-9]{1}")
                        + faker.regexify("[" + specialChars + "]{1}")
                        + faker.lorem().characters(7);

                encodedPassword = passwordEncoder.encode(rawPassword);

            } while (!Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@\"$!%*?&'()*+-./]{4,}$", rawPassword));

            log.info(rawPassword + " hasło");

            User user = User.builder()
                    .email(faker.internet().emailAddress())
                    .createdAt(String.valueOf(new Timestamp(System.currentTimeMillis())))
                    .updatedAt(String.valueOf(new Timestamp(System.currentTimeMillis())))
                    .firstName(faker.name().firstName())
                    .lastName(faker.name().lastName())
                    .password(encodedPassword)
                    .isLock(false)
                    .isEnabled(true)
                    .role(Role.USER)
                    .build();

            usersFake.add(user);
            log.info(user.getEmail() + " email użytkownika");
        }

        usersFake = userRepository.saveAll(usersFake);
    }

    public void generateSkills() {
        for (int i = 0; i < 20; i++) {
            Skill skill = Skill.builder()
                    .name(faker.job().keySkills())
                    .build();

            skillList.add(skill);
        }
        skillList = skillRepository.saveAll(skillList);
    }

    @Transactional
    public void generateJobs() {
        List<Skill> managedSkills = skillRepository.findAll();
        for (int i = 0; i < 30; i++) {
            Collections.shuffle(managedSkills);
            List<Skill> selectedSkills = managedSkills.stream().limit(3).collect(Collectors.toList());
            Job job = Job.builder()
                    .title(faker.job().title())
                    .salary((long) faker.number().numberBetween(10000, 40000))
                    .currency(Currency.PLN)
                    .skills(selectedSkills)
                    .city(faker.address().city())
                    .build();

            jobsFake.add(job);
        }
        jobsFake = jobRepository.saveAll(jobsFake);
    }


    public void generateCandidates() {
        List<User> users = userRepository.findAll();
        for (int i = 0; i < 30; i++) {
            Candidate candidate = Candidate.builder()
                    .email(faker.internet().emailAddress())
                    .createdBy(users.get(i))
                    .build();
            candidateFake.add(candidate);
        }
        candidateFake = candidateRepository.saveAll(candidateFake);
    }

    @Transactional
    public void generateRecruitments() {
        List<Job> jobs = jobRepository.findAll();
        List<Candidate> candidates = candidateRepository.findAll();
        for (int i = 0; i < 30; i++) {
            Collections.shuffle(jobs);
            Collections.shuffle(candidates);
            Job selectedJob = jobs.get(i);
            Candidate selectedCandidate = candidates.get(i);
            Recruitment recruitment = Recruitment.builder()
                    .job(selectedJob)
                    .candidate(selectedCandidate)
                    .build();
            recruitmentList.add(recruitment);
        }
        recruitmentList = recruitmentRepository.saveAll(recruitmentList);
    }
}



