package com.spring.hrrecruitmentbackend.registration.service;


import com.spring.hrrecruitmentbackend.registration.model.entity.ResetOperations;
import com.spring.hrrecruitmentbackend.registration.repository.ResetOperationsRepository;
import com.spring.hrrecruitmentbackend.user.model.entity.User;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
@EnableScheduling
@Slf4j
@NoArgsConstructor
public class ResetOperationService {


    private ResetOperationsRepository resetOperationsRepository;

    @Autowired
    public ResetOperationService(ResetOperationsRepository resetOperationsRepository) {
        this.resetOperationsRepository = resetOperationsRepository;
    }


    @Transactional
    public ResetOperations initResetOperation(User user){
        ResetOperations resetOperations = new ResetOperations();

        resetOperations.setUuid(UUID.randomUUID().toString());
        resetOperations.setCreateDate(new Timestamp(System.currentTimeMillis()).toString());
        resetOperations.setUser(user);

        resetOperationsRepository.deleteAllByUser(user);
        return resetOperationsRepository.saveAndFlush(resetOperations);
    }

    public void endOperation(String uid){
        resetOperationsRepository.findByUuid(uid).ifPresent(resetOperationsRepository::delete);
    }

    @Scheduled(cron = "0 0/1 * * * *")
    protected void deleteExpireOperation(){
      List<ResetOperations> resetOperations = resetOperationsRepository.findExpiredOperations();
      log.info("Find {} expired operations to delete",resetOperations.size());
      if (!resetOperations.isEmpty()){
          resetOperationsRepository.deleteAll(resetOperations);
      }
    }

}
