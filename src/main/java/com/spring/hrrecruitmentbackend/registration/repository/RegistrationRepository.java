package com.spring.hrrecruitmentbackend.registration.repository;

import com.spring.hrrecruitmentbackend.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepository extends JpaRepository<User, String> {

}
