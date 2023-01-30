package com.thesis.medicalapp.config;

import com.thesis.medicalapp.repository.OTPRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Date;

@Service
@Transactional
@Slf4j
public class TokenPurgeTask {
    @Autowired
    private OTPRepository otpRepository;

    @Scheduled(cron = "${purge.cron.expression}")
    public void purgeExpired() {
        Date now = Date.from(Instant.now());
        otpRepository.deleteByExpireDateLessThan(now);
    }
}
