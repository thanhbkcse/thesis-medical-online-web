package com.thesis.medicalapp.services.impl;

import com.thesis.medicalapp.config.TwilioConfig;
import com.thesis.medicalapp.models.Sms;
import com.thesis.medicalapp.services.SmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SmsServiceImpl implements SmsService {
    @Autowired
    TwilioConfig twilioConfig;

    @Override
    public void sendSMS(Sms sms) {
        try {
            log.info("Send token {} to {}", sms.getMessage(), sms.getTo());
            twilioConfig.send(sms);
        } catch (Exception e) {
            throw e;
        }
    }
}
