package com.thesis.medicalapp.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class VerificationRequest {
    @NotEmpty
    @NotBlank
    private String username;
    @NotEmpty
    @NotBlank
    private String otp;
}
