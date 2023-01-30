package com.thesis.medicalapp.controllers;

import com.thesis.medicalapp.models.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class MessageController {
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/application")
    @SendTo("/all/messages")
    public Notification send(Notification notification) throws Exception {
        System.out.println(notification);
        return notification;
    }

    @MessageMapping("/private")
    public void sendToSpecificUser(@Payload Notification notification) {
        simpMessagingTemplate.convertAndSendToUser(notification.getToUser(), "/user/queue/notification", notification);
    }
}
