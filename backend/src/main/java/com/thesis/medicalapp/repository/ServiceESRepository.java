package com.thesis.medicalapp.repository;

import com.thesis.medicalapp.models.HospitalES;
import com.thesis.medicalapp.models.HospitalServiceES;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ServiceESRepository extends ElasticsearchRepository<HospitalServiceES, String> {
    @Query("{\"query_string\": {\"default_field\": \"name\", \"query\": \"*?0*\"}}")
    Page<HospitalServiceES> findByName(String name, Pageable pageable);
    void deleteAllByHospitalId(String id);
}
