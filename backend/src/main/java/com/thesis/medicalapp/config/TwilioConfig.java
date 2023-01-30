package com.thesis.medicalapp.config;

import com.thesis.medicalapp.models.Sms;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Data
@Component
public class TwilioConfig {
    @Value("${twilio.account_sid}")
    private String ACCOUNT_SID;
    @Value("${twilio.auth_token}")
    private String AUTH_TOKEN;
    @Value("${twilio.trial_number}")
    private String FROM_NUMBER;

    public void send(Sms sms) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(new PhoneNumber(sms.getTo()), new PhoneNumber(FROM_NUMBER), sms.getMessage())
                .create();
        System.out.println("here is my id:"+message.getSid());
    }
}
