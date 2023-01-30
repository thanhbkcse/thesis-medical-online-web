package com.thesis.medicalapp.services;

import com.thesis.medicalapp.models.Hospital;
import com.thesis.medicalapp.pojo.HospitalDTO;
import com.thesis.medicalapp.search.SearchRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface HospitalService {
    Hospital saveHospital(HospitalDTO hospitalDTO);
    Hospital update(Hospital hospital);
    void delete(String id);
    List<HospitalDTO> getHospitals();
    HospitalDTO getHospitalById(String id);
    Boolean existsById(String id);
    Page<Hospital> search(SearchRequest request);
}
