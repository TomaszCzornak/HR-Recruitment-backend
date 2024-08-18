package com.spring.hrrecruitmentbackend.job.exceptions;

import com.spring.hrrecruitmentbackend.job.api.JobsController;
import com.spring.hrrecruitmentbackend.utils.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice(assignableTypes = JobsController.class)
public class JobsControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmptyJobsListException.class)
    public ResponseEntity<Object> handleException(EmptyJobsListException e, WebRequest request) {
        HttpStatus status = HttpStatus.NO_CONTENT;
        String message = "Unfortunately, there are no jobs";
        ApiError apiError = new ApiError(status, message);
        return new  ResponseEntity<>(apiError, status);
    }

    @ExceptionHandler(JobNotFoundException.class)
    public ResponseEntity<Object> handleException(JobNotFoundException e, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String message = "There is no such job under given id";
        ApiError apiError = new ApiError(status, message);
        return new  ResponseEntity<>(apiError, status);
    }

}
