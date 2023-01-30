package com.thesis.medicalapp.repository;

import com.thesis.medicalapp.models.Doctor;
import com.thesis.medicalapp.models.Hospital;
import com.thesis.medicalapp.models.HospitalService;
import com.thesis.medicalapp.search.SearchSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<HospitalService, String>, JpaSpecificationExecutor<HospitalService> {
    List<HospitalService> findByHospitalId(String hospitalId);
    Page<HospitalService> findAllByHospitalId(String hospitalId, Pageable pageable);
    Page<HospitalService> findAllByHospital_Id(String hospitalId, SearchSpecification specification, Pageable pageable);
}
