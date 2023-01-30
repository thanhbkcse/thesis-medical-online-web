package com.thesis.medicalapp.repository;

import com.thesis.medicalapp.models.LeaveRequest;
import com.thesis.medicalapp.models.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String>, JpaSpecificationExecutor<LeaveRequest> {
    Page<Notification> findAllByToUser(String toUser, Pageable pageable);
}
