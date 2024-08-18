package com.spring.hrrecruitmentbackend.job.model.request;

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
public class JobRequest {

    private String title;
    private String city;
    private Long salary;
    private Currency currency;
    private List<Skill> skills;


}
