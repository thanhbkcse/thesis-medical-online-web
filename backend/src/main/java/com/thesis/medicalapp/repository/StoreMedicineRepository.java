package com.thesis.medicalapp.repository;

import com.thesis.medicalapp.models.StoreMedicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreMedicineRepository extends JpaRepository<StoreMedicine, String>, JpaSpecificationExecutor<StoreMedicine> {
    List<StoreMedicine> findByHospitalId(String hospitalId);
    boolean existsById(String id);
}
