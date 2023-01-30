package com.thesis.medicalapp.services;

import com.thesis.medicalapp.models.Hospital;
import com.thesis.medicalapp.models.HospitalES;
import com.thesis.medicalapp.search.SearchRequest;
import org.springframework.data.domain.Page;

public interface HospitalESService {
    HospitalES save(Hospital hospital);
    Page<HospitalES> search(String name);
    void delete(String id);
}
