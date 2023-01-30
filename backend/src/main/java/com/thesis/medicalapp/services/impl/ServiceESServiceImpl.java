package com.thesis.medicalapp.services.impl;

import com.thesis.medicalapp.models.HospitalService;
import com.thesis.medicalapp.models.HospitalServiceES;
import com.thesis.medicalapp.repository.ServiceESRepository;
import com.thesis.medicalapp.services.ServiceESService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ServiceESServiceImpl implements ServiceESService {
    private final ServiceESRepository serviceESRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    @Async
    public HospitalServiceES save(HospitalService hospitalService) {
        HospitalServiceES hospitalServiceES = modelMapper.map(hospitalService, HospitalServiceES.class);
        return serviceESRepository.save(hospitalServiceES);
    }

    @Override
    @Async
    public void deleteAllByHospitalId(String id) {
        serviceESRepository.deleteAllByHospitalId(id);
    }

    @Override
    @Async
    public void delete(String id) {
        serviceESRepository.deleteById(id);
    }

    @Override
    public Page<HospitalServiceES> search(String name) {
        return serviceESRepository.findByName(name, PageRequest.of(0, 8));
    }
}
