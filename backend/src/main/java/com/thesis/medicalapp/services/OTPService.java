package com.thesis.medicalapp.services;

import com.thesis.medicalapp.models.OTP;
import com.thesis.medicalapp.models.User;

public interface OTPService {
    OTP generateOTP(User user);
    void verifyUser(String username, String otp) throws Exception;
}
