package com.spring.hrrecruitmentbackend.job.model.response;

import com.spring.hrrecruitmentbackend.job.model.entity.Currency;
import com.spring.hrrecruitmentbackend.recruitment.entity.Recruitment;
import com.spring.hrrecruitmentbackend.skills.entity.Skill;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class JobResponse {
    private Long id;
    private String title;
    private String city;
    private Long salary;
    private Currency currency;
    private List<Skill> skills;
    private List<Recruitment> recruitmentList;
}
