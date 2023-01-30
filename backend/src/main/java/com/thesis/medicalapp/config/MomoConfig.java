package com.thesis.medicalapp.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Data
@Component
public class MomoConfig {
    @Value("${momo.access_key}")
    private String ACCESS_KEY;
    @Value("${momo.secret_key}")
    private String SECRET_KEY;
    @Value("${momo.partner_code}")
    private String PARTNER_CODE;
}
