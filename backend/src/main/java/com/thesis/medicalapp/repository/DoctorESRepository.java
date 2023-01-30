package com.thesis.medicalapp.repository;

import com.thesis.medicalapp.models.DoctorES;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface DoctorESRepository extends ElasticsearchRepository<DoctorES, String> {
    @Query("{\"query_string\": {\"fields\": [\"name\", \"specialty\"], \"query\": \"*?0*\"}}")
    Page<DoctorES> findByName(String name, Pageable pageable);
    void deleteAllByHospitalId(String id);
}
