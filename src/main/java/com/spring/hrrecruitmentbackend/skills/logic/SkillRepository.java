package com.spring.hrrecruitmentbackend.skills.logic;

import com.spring.hrrecruitmentbackend.skills.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {

    Skill findByName(String name);

}
