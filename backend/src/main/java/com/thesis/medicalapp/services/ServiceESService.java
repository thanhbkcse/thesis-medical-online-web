package com.thesis.medicalapp.services;

import com.thesis.medicalapp.models.HospitalES;
import com.thesis.medicalapp.models.HospitalService;
import com.thesis.medicalapp.models.HospitalServiceES;
import org.springframework.data.domain.Page;

public interface ServiceESService {
    HospitalServiceES save(HospitalService hospitalService);
    Page<HospitalServiceES> search(String name);
    void delete(String id);
    void deleteAllByHospitalId(String id);
}
