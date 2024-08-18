package com.spring.hrrecruitmentbackend.recruitment.repository;

import com.spring.hrrecruitmentbackend.recruitment.entity.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruitmentRepository extends JpaRepository<Recruitment, Long> {

}
