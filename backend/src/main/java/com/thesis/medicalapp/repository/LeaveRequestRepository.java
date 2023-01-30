package com.thesis.medicalapp.repository;

import com.thesis.medicalapp.models.Address;
import com.thesis.medicalapp.models.Doctor;
import com.thesis.medicalapp.models.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, String>, JpaSpecificationExecutor<LeaveRequest> {
}
