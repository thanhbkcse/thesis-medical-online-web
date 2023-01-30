package com.thesis.medicalapp.repository;

import com.thesis.medicalapp.models.Hospital;
import com.thesis.medicalapp.models.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, String>, JpaSpecificationExecutor<Record> {
    List<Record> findByAppointment_Profile_IdOrderByCreatedAtDesc(String profileId);
    Record findByAppointment_Id(String appointmentId);
}
