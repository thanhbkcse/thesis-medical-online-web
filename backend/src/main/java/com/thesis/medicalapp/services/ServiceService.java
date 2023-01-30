package com.thesis.medicalapp.services;

import com.thesis.medicalapp.models.Hospital;
import com.thesis.medicalapp.models.HospitalService;
import com.thesis.medicalapp.payload.ServiceRequest;
import com.thesis.medicalapp.pojo.HospitalDTO;
import com.thesis.medicalapp.pojo.ServiceDTO;
import com.thesis.medicalapp.search.SearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ServiceService {
    HospitalService save(ServiceRequest serviceRequest);
    HospitalService update(HospitalService hospitalService);
    Iterable<HospitalService> getAll();
    void delete(String id);
    Page<HospitalService> search(SearchRequest request);
    Page<HospitalService> findAllByHospitalId(String hospitalId, Pageable pageable);
}
