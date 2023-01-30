package com.thesis.medicalapp.payload;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.*;
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class SignupRequest {
    @NotEmpty
    @NotBlank
    private String username;
    @NotEmpty
    @NotBlank
    private String phone;
    @NotEmpty
    @NotBlank
    private String password;
    private String role;
}
