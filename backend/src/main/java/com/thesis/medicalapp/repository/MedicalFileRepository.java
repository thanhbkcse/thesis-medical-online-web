package com.thesis.medicalapp.repository;

import com.thesis.medicalapp.models.Hospital;
import com.thesis.medicalapp.models.MedicalFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalFileRepository extends JpaRepository<MedicalFile, String> {
}
