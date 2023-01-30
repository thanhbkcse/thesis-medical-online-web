package com.thesis.medicalapp.services.impl;

import com.thesis.medicalapp.models.Hospital;
import com.thesis.medicalapp.models.HospitalES;
import com.thesis.medicalapp.models.StoreMedicine;
import com.thesis.medicalapp.repository.DoctorESRepository;
import com.thesis.medicalapp.repository.HospitalESRepository;
import com.thesis.medicalapp.search.SearchRequest;
import com.thesis.medicalapp.search.SearchSpecification;
import com.thesis.medicalapp.services.HospitalESService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class HospitalESServiceImpl implements HospitalESService {
    private final HospitalESRepository hospitalESRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    @Async
    public HospitalES save(Hospital hospital) {
        HospitalES hospitalES = modelMapper.map(hospital, HospitalES.class);

        return hospitalESRepository.save(hospitalES);
    }

    @Override
    @Async
    public void delete(String id) {
        hospitalESRepository.deleteById(id);
    }

    @Override
    public Page<HospitalES> search(String name) {
        return hospitalESRepository.findByName(name, PageRequest.of(0, 8));
    }
}
