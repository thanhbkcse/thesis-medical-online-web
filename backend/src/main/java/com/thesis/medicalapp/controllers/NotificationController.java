package com.thesis.medicalapp.controllers;

import com.thesis.medicalapp.models.Global;
import com.thesis.medicalapp.models.LeaveRequest;
import com.thesis.medicalapp.models.Notification;
import com.thesis.medicalapp.payload.response.ApiResponse;
import com.thesis.medicalapp.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationRepository notificationRepository;
    @GetMapping("")
    public ResponseEntity<Object> getNotifications(
            @RequestParam Integer size,
            @RequestParam Integer page
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "time"));
        Page<Notification> notifications = notificationRepository.findAllByToUser(Global.user.getUsername(), pageable);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(notifications.getContent(), notifications)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getNotifications(
        @PathVariable String id
    ) {
        Notification notification = notificationRepository.findById(id).get();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(notification)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteNotifications(
            @PathVariable String id
    ) {
        notificationRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(null)
        );
    }

    @PostMapping("/read/{id}")
    public ResponseEntity<Object> readNotification(
            @PathVariable String id
    ) {
        Notification notification = notificationRepository.findById(id).get();
        notification.setRead(Boolean.TRUE);
        notificationRepository.save(notification);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(null)
        );
    }
}
