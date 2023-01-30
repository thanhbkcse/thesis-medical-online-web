package com.thesis.medicalapp.services;

import com.thesis.medicalapp.models.Doctor;
import com.thesis.medicalapp.models.DoctorES;
import org.springframework.data.domain.Page;

public interface DoctorESService {
    DoctorES save(Doctor doctor);
    Page<DoctorES> search(String name);
    void delete(String id);
    void deleteAllByHospitalId(String id);
}
