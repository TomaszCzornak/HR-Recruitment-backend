package com.spring.hrrecruitmentbackend.job.api;

import com.spring.hrrecruitmentbackend.job.model.entity.Job;
import com.spring.hrrecruitmentbackend.job.model.request.JobRequest;
import com.spring.hrrecruitmentbackend.job.model.response.JobResponse;
import com.spring.hrrecruitmentbackend.job.service.JobService;
import com.spring.hrrecruitmentbackend.utils.JobMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.spring.hrrecruitmentbackend.job.exceptions.EmptyJobsListException;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/jobs")
public class JobsController {

    private final JobService jobService;


    @RequestMapping()
    public List<JobResponse> getAllJobs() {
        List<JobResponse> jobResponses = jobService.getAllJobs()
                .stream()
                .map(JobMapper::toJobResponse)
                .toList();

        return Optional.of(jobResponses)
                .filter(list -> !list.isEmpty())
                .orElseThrow(EmptyJobsListException::new);
    }


    @PostMapping(consumes = "application/json", produces = "application/json")
    @Operation(summary = "Add a Job", description = "This endpoint is for adding a new Job", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Job added succesfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request due to validation failure")})
    public void addJobSubmit(@RequestBody @Valid JobRequest jobRequest) {
        jobService.addJob(jobRequest);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Job by ID", description = "This endpoint is used to fetch a Job with a specific id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the job"),
            @ApiResponse(responseCode = "404", description = "Job with provided id not found")
    })
    public JobResponse getSingleJob(@PathVariable("id") @Parameter(description = "ID of the job to be fetched") Long id) {
        return jobService.getJobById(id);
    }


    @PutMapping(consumes = "application/json", produces = "application/json")
    @Operation(summary = "Update a Job", description = "This endpoint is for updating a Job")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Job updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request due to validation failure")
    })
    public Job updateJob(@RequestBody
                         @Parameter(description = "The Job to be updated. Validated with standard job validations.")
                         @Valid Job job) {

        return jobService.updateJob(job);

    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Job", description = "This endpoint is for deleting a Job with a specific id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted the job"),
            @ApiResponse(responseCode = "404", description = "Job with provided id not found"),
    })
    public void deleteJob(@PathVariable("id")
                          @Parameter(description = "ID of the job to be deleted") Long id) {
        jobService.deleteJobById(id);
    }

}