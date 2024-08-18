package com.spring.hrrecruitmentbackend.job.model.dto;

import com.spring.hrrecruitmentbackend.candidate.model.dto.CandidateDto;
import com.spring.hrrecruitmentbackend.skills.entity.Skill;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.spring.hrrecruitmentbackend.job.model.entity.Currency;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobDto {

    private Long id;
    private String title;
    private String city;
    private long salary;
    private Currency currency;
    private List<Skill> skills;
    private List<CandidateDto> candidates;

}
