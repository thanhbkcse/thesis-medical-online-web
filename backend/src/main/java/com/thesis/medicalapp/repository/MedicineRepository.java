package com.thesis.medicalapp.repository;

import com.thesis.medicalapp.models.Medicine;
import com.thesis.medicalapp.models.OTP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineRepository extends JpaRepository<Medicine, String> {
}
