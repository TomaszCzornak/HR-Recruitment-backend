package com.spring.hrrecruitmentbackend.candidate.service;

import com.spring.hrrecruitmentbackend.candidate.model.dto.CandidateDto;
import com.spring.hrrecruitmentbackend.candidate.model.entity.Candidate;
import com.spring.hrrecruitmentbackend.candidate.model.request.CandidateRequest;
import com.spring.hrrecruitmentbackend.candidate.model.response.CandidateResponse;
import com.spring.hrrecruitmentbackend.candidate.repository.CandidateRepository;
import com.spring.hrrecruitmentbackend.user.model.entity.User;
import com.spring.hrrecruitmentbackend.user.service.UsersService;
import com.spring.hrrecruitmentbackend.utils.CandidateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.spring.hrrecruitmentbackend.candidate.exceptions.CandidateNotFoundException;

import javax.ws.rs.NotFoundException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CandidateService {

    private final CandidateRepository candidateRepository;
    private final UsersService usersService;

    public List<CandidateResponse> getAllCandidates() {
        return CandidateMapper.toCandidateResponseList(candidateRepository.findAll());
    }

    public CandidateResponse getCandidateById(Long id) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Candidate not found with id = " + id));
        return CandidateMapper.toCandidateResponse(candidate);
    }

    public CandidateResponse addCandidate(CandidateRequest candidateRequest) {

        User userLogged = usersService.getLoggedUser();

        CandidateDto candidateDto = CandidateMapper.toCandidateDto(candidateRequest, userLogged);
        if (candidateDto != null) {
            Candidate candidate = CandidateMapper.toCandidateEntity(candidateDto);
            candidate.setCreatedBy(userLogged);
            return CandidateMapper.toCandidateResponse(candidateRepository.save(candidate));
        } else {
            throw new IllegalStateException("Candidate not added");
        }
    }

    public CandidateResponse updateCandidate(Long id, CandidateRequest candidateRequest) {
        Candidate candidate = CandidateMapper.toCandidateEntity(candidateRequest);

        User userLogged = usersService.getLoggedUser();
        return candidateRepository.findById(id).map(candidateFound -> {
                    candidateFound.setEmail(candidate.getEmail());
                    candidateFound.setCreatedBy(User.builder().email(userLogged.getEmail())
                                    .createdAt(userLogged.getCreatedAt()).build());
                    candidateFound.setRecruitmentList(candidateFound.getRecruitmentList());
                    return CandidateMapper.toCandidateResponse(candidateRepository.save(candidateFound));
                })
                .orElseThrow(CandidateNotFoundException::new);
    }

    public void deleteCandidate(Long id) {
        candidateRepository.deleteById(id);
    }

    public Candidate getCandidateByEmail(String email) {
        return candidateRepository.findCandidateByEmail(email);
    }
}
