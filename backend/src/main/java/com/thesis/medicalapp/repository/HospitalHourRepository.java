package com.thesis.medicalapp.repository;

import com.thesis.medicalapp.models.Doctor;
import com.thesis.medicalapp.models.HospitalHour;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalHourRepository extends JpaRepository<HospitalHour, String> {
}
