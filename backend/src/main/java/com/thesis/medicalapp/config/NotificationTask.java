package com.thesis.medicalapp.config;

import com.thesis.medicalapp.models.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
@Slf4j
public class NotificationTask {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Scheduled(fixedRate = 60000)
    public void pushNotification() throws Exception {
        Notification notification = new Notification();
        notification.setText("Hello from Server!");
        notification.setTime(new Date());
        simpMessagingTemplate.convertAndSendToUser("user","/queue/notification", notification);
    }
}
