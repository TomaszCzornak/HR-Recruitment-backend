package com.spring.hrrecruitmentbackend.recruitment.service;

import com.spring.hrrecruitmentbackend.Utils.JobStub;
import com.spring.hrrecruitmentbackend.Utils.RecruitmentStub;
import com.spring.hrrecruitmentbackend.candidate.service.CandidateService;
import com.spring.hrrecruitmentbackend.job.model.dto.JobDto;
import com.spring.hrrecruitmentbackend.job.model.entity.Job;
import com.spring.hrrecruitmentbackend.job.service.JobService;
import com.spring.hrrecruitmentbackend.recruitment.entity.Recruitment;
import com.spring.hrrecruitmentbackend.recruitment.model.request.RecruitmentRequest;
import com.spring.hrrecruitmentbackend.recruitment.repository.RecruitmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.spring.hrrecruitmentbackend.recruitment.model.response.RecruitmentResponse;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class RecruitmentServiceTest {

    @InjectMocks
    private RecruitmentService recruitmentService;
    @Mock
    private CandidateService candidateService;
    @Mock
    private RecruitmentRepository recruitmentRepositoryMock;
    @Mock
    private JobService jobServiceMock;

    @BeforeEach
    void setUp() {
        recruitmentService = new RecruitmentService(recruitmentRepositoryMock,
                candidateService, jobServiceMock);
    }
    @Test
    void getAllRecruitments_shouldReturnAllRecruitments() {
        //given
        List<Recruitment> recruitments = RecruitmentStub.createRecruitmentList();
        given(recruitmentRepositoryMock.findAll()).willReturn(recruitments);
        //when
        List<RecruitmentResponse> result = recruitmentService.getAllRecruitments();

        //then
        assertEquals(recruitments.size(), result.size());
        assertEquals(recruitments.get(0).getCandidate().getEmail(), result.get(0).getCandidateDto().getEmail());
    }

    @Test
    void getRecruitmentById_shouldReturnRecruitmentWithThisId() {
        Long id = 1L;
        Recruitment recruitment = RecruitmentStub.createRecruitment();
        recruitment.getJob().setTitle("Front End Developer");
        recruitment.getCandidate().setEmail("front.end.developer@gmail.com");
        given(recruitmentRepositoryMock.findById(id)).willReturn(Optional.of(recruitment));

        RecruitmentResponse result = recruitmentService.getRecruitmentById(id);

        assertEquals(recruitment.getJob().getTitle(), result.getJobDto().getTitle());
        assertEquals(recruitment.getCandidate().getEmail(), result.getCandidateDto().getEmail());
    }

    @Test
    void updateRecruitment_shouldUpdateRecruitment() {
        //given
        final Long id = 1L;
        Recruitment recruitmentBefore = RecruitmentStub.createRecruitment();
        Job job = JobStub.createJob();
        job.setTitle("Old Title");
        recruitmentBefore.setJob(job);
        Recruitment recruitmentAfter = RecruitmentStub.createRecruitment();
        Job jobAfter = JobStub.createJob();
        jobAfter.setTitle("New Title");
        recruitmentAfter.setJob(jobAfter);
        RecruitmentRequest recruitmentRequest = RecruitmentStub.createRecruitmentRequest();
        recruitmentRequest.setJobDto(JobDto.builder().title("New Title").build());
        given(recruitmentRepositoryMock.save(any(Recruitment.class))).willReturn(recruitmentBefore);

        //when
        recruitmentService.updateRecruitment(recruitmentRequest);
        recruitmentRepositoryMock.save(recruitmentBefore);
        //then
        assertEquals(recruitmentRequest.getJobDto().getTitle(), recruitmentAfter.getJob().getTitle());
    }

    @Test
    void deleteRecruitment() {
        //given
        final Long id = 1L;
        doNothing().when(recruitmentRepositoryMock).deleteById(id);
        //when
        recruitmentService.deleteRecruitment(id);
        //then
        verify(recruitmentRepositoryMock).deleteById(id);
    }

    @Test
    void addRecruitment_shouldAddRecruitment() {
        //given
        RecruitmentRequest recruitmentRequest = RecruitmentStub.createRecruitmentRequest();
        Recruitment recruitment = RecruitmentStub.createRecruitment();
        given(recruitmentRepositoryMock.save(any(Recruitment.class))).willReturn(recruitment);
        given(recruitmentRepositoryMock.findAll()).willReturn(RecruitmentStub.createRecruitmentList());
        given(candidateService.getCandidateByEmail(recruitmentRequest.getCandidateDto().getEmail())).willReturn(recruitment.getCandidate());
        given(jobServiceMock.getJobByTitle(recruitmentRequest.getJobDto().getTitle())).willReturn(Optional.ofNullable(recruitment.getJob()));
        //when
        recruitmentService.addRecruitment(recruitmentRequest);
        List<RecruitmentResponse> all = recruitmentService.getAllRecruitments();
        //then
        verify(recruitmentRepositoryMock).save(any(Recruitment.class));
        assertEquals(recruitment.getJob().getTitle(), all.get(0).getJobDto().getTitle());
    }
}