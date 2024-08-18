package com.spring.hrrecruitmentbackend.registration.api;

import com.spring.hrrecruitmentbackend.registration.model.request.ChangePasswordData;
import com.spring.hrrecruitmentbackend.registration.model.request.RegistrationRequest;
import com.spring.hrrecruitmentbackend.registration.model.request.ResetPasswordData;
import com.spring.hrrecruitmentbackend.registration.model.response.ActivationResponse;
import com.spring.hrrecruitmentbackend.registration.service.RegistrationService;
import com.spring.hrrecruitmentbackend.registration.service.UserRegistrationFacade;
import com.spring.hrrecruitmentbackend.user.exceptions.UserNotFoundException;
import com.spring.hrrecruitmentbackend.user.model.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.io.IOException;
import java.sql.Timestamp;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@Slf4j
public class RegistrationController {

    private final UserRegistrationFacade userRegistrationFacade;
    private final RegistrationService registrationService;

    @PostMapping("/register")
    @Operation(summary = "Register User", description = "This endpoint is for user registration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully registered"),
            @ApiResponse(responseCode = "400", description = "Bad request due to validation failure")
    })
    public UserResponse register(@RequestBody
                                 @Parameter(description = "The RegistrationRequest object that is validated for registration")
                                 @Valid RegistrationRequest registrationRequest) throws IOException {

        return userRegistrationFacade.registerUser(registrationRequest);
    }

    @RequestMapping(path = "/activate", method = RequestMethod.GET)
    public ResponseEntity<ActivationResponse> activateUser(@RequestParam String uuid) {
        try {
            log.info("--START activateUser");
            registrationService.activateUser(uuid);
            log.info("--STOP activateUser");
            return ResponseEntity.ok(new ActivationResponse(uuid, new Timestamp(System.currentTimeMillis()), "Konto zostało aktywowane"));
        } catch (UserNotFoundException e) {
            String errorMessage = e.getMessage();
            return ResponseEntity.status(400).body(new ActivationResponse(errorMessage));
        }
    }

    @Operation(
            summary = "Sends email with link to reset password",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResetPasswordData.class))
            )
    )
    @PostMapping(value = "/reset-password", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ActivationResponse> sendMailRecovery(@RequestBody ResetPasswordData resetPasswordData) {
        try {
            log.info("--START sendMailRecovery");
            registrationService.recoveryPassword(resetPasswordData.getEmail());
            log.info("--STOP sendMailRecovery");
            return ResponseEntity.ok(new ActivationResponse("Sukces"));
        } catch (UserNotFoundException e) {
            String errorMessage = e.getMessage();
            return ResponseEntity.status(400).body(new ActivationResponse(errorMessage));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(path = "/reset-password", method = RequestMethod.PATCH)
    public ResponseEntity<ActivationResponse> recoveryMail(@RequestBody ChangePasswordData changePasswordData) {
        try {
            registrationService.resetPassword(changePasswordData);
            return ResponseEntity.ok(new ActivationResponse(changePasswordData.getUuid(), new Timestamp(System.currentTimeMillis()), "Sukces: udało się zresetować hasło"));
        } catch (UserNotFoundException e) {
            String errorMessage = e.getMessage();
            return ResponseEntity.status(400).body(new ActivationResponse(errorMessage));
        }
    }

}
