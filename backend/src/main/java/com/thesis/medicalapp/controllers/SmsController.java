package com.thesis.medicalapp.controllers;

import com.thesis.medicalapp.config.TwilioConfig;
import com.thesis.medicalapp.models.OTP;
import com.thesis.medicalapp.models.Sms;
import com.thesis.medicalapp.payload.response.ApiResponse;
import com.thesis.medicalapp.repository.OTPRepository;
import com.thesis.medicalapp.services.OTPService;
import com.thesis.medicalapp.services.UserService;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.twilio.Twilio;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SmsController {
    @Autowired
    TwilioConfig twilioConfig;
    private final OTPService otpService;
    private final UserService userService;

    @PostMapping(value = "/send-sms")
    public ResponseEntity<ApiResponse> sendSMS(@RequestBody Sms sms) {
        try {

            OTP otp = new OTP();
            twilioConfig.send(sms);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse<>(HttpStatus.BAD_REQUEST, e.getMessage())
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(HttpStatus.OK.value(), HttpStatus.OK.toString())
        );
    }
}
