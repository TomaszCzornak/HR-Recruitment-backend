package com.spring.hrrecruitmentbackend.candidate.exceptions;

import com.spring.hrrecruitmentbackend.candidate.api.CandidateController;
import com.spring.hrrecruitmentbackend.utils.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice(assignableTypes = CandidateController.class)
public class CandidateControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmptyCandidateListException.class)
    public ResponseEntity<Object> handleException(EmptyCandidateListException e, WebRequest request) {
        String message = "No candidates found";
        return handle(HttpStatus.NO_CONTENT, message);
    }

    @ExceptionHandler(CandidateNotFoundException.class)
    public ResponseEntity<Object> handleException(CandidateNotFoundException e, WebRequest request) {
        String message = "No candidate found under given id";
        return handle(HttpStatus.NOT_FOUND, message);
    }

    private ResponseEntity<Object> handle(HttpStatus status, String message) {
        ApiError apiError = new ApiError(status, message);
        return new ResponseEntity<>(apiError, status);
    }
}