package com.thesis.medicalapp.repository;

import com.thesis.medicalapp.models.HospitalES;
import com.thesis.medicalapp.models.HospitalServiceES;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface HospitalESRepository extends ElasticsearchRepository<HospitalES, String> {
    @Query("{\"query_string\": {\"default_field\": \"name\", \"query\": \"*?0*\"}}")
    Page<HospitalES> findByName(String name, Pageable pageable);
}
