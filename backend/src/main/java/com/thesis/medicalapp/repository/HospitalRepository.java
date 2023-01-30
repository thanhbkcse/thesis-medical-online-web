package com.thesis.medicalapp.repository;

import com.thesis.medicalapp.models.Hospital;
import com.thesis.medicalapp.models.HospitalService;
import com.thesis.medicalapp.models.StoreMedicine;
import com.thesis.medicalapp.search.SearchSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HospitalRepository extends JpaRepository<Hospital, String>, JpaSpecificationExecutor<Hospital> {
    String FIND_HOSPITALS = "SELECT id, name FROM hospitals";

    @Query(value = FIND_HOSPITALS, nativeQuery = true)
    List<Object[]> findAllForListing();
    Hospital findByName(String name);
}
