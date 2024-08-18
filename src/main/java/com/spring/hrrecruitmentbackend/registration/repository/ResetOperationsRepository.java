package com.spring.hrrecruitmentbackend.registration.repository;

import com.spring.hrrecruitmentbackend.registration.model.entity.ResetOperations;
import com.spring.hrrecruitmentbackend.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;


@Repository
public interface ResetOperationsRepository extends JpaRepository<ResetOperations, Long> {

    @Modifying
    void deleteAllByUser(User user);

    Optional<ResetOperations> findByUuid(String uid);


    /**
     *
     * query compatible with Postgresql
     *  @Query(nativeQuery = true, value = "SELECT * FROM resetoperations where CAST(createdate AS timestamp) <= current_timestamp - INTERVAL '15 minutes'")
     * query compatible with H2 database
     * @Query(nativeQuery = true, value = "SELECT * FROM resetoperations WHERE createdate <= DATEADD('MINUTE', -15, CURRENT_TIMESTAMP)")
     */
    @Query(nativeQuery = true, value = "SELECT * FROM resetoperations WHERE createdate <= DATEADD('MINUTE', -15, CURRENT_TIMESTAMP)")
    List<ResetOperations> findExpiredOperations();


}
