package com.thesis.medicalapp.services.impl;

import com.thesis.medicalapp.exception.ApiRequestException;
import com.thesis.medicalapp.models.Hospital;
import com.thesis.medicalapp.models.HospitalService;
import com.thesis.medicalapp.payload.ServiceRequest;
import com.thesis.medicalapp.pojo.ServiceDTO;
import com.thesis.medicalapp.repository.HospitalRepository;
import com.thesis.medicalapp.repository.ServiceRepository;
import com.thesis.medicalapp.search.SearchRequest;
import com.thesis.medicalapp.search.SearchSpecification;
import com.thesis.medicalapp.services.ServiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ServiceServiceImpl implements ServiceService {
    private final ServiceRepository serviceRepository;
    private final HospitalRepository hospitalRepository;

    @Override
    public HospitalService save(ServiceRequest serviceRequest) {
        HospitalService hospitalService = new HospitalService();
        Optional<Hospital> hospital = hospitalRepository.findById(serviceRequest.getHospitalId());
        if (!hospital.isPresent()) throw new ApiRequestException("Không tìm thấy bệnh viện!");
        hospitalService.setHospital(hospital.get());
        hospitalService.setInfo(serviceRequest.getInfo());
        hospitalService.setName(serviceRequest.getName());
        hospitalService.setPrice(serviceRequest.getPrice());
        hospitalService.setRegistrationNumber(0);
        hospitalService.setFavorite(0.0);
        hospitalService.setNumOfServicePerHour(serviceRequest.getNumOfServicePerHour());
        hospitalService.setType(serviceRequest.getType());
        hospitalService.setSpecialty(serviceRequest.getSpecialty());
        hospitalService.setImageUrl(serviceRequest.getImageUrl());
        HospitalService hospitalServiceRes = serviceRepository.save(hospitalService);
        return hospitalServiceRes;
    }

    @Override
    public void delete(String id) {
        serviceRepository.deleteById(id);
    }

    @Override
    public HospitalService update(HospitalService hospitalService) {
        if (!serviceRepository.existsById(hospitalService.getId()))
            throw new ApiRequestException("Không tìm thấy dịch vụ!");
        return serviceRepository.save(hospitalService);
    }

    @Override
    public Iterable<HospitalService> getAll() {
        return serviceRepository.findAll();
    }

    @Override
    public Page<HospitalService> search(SearchRequest request) {
        SearchSpecification<HospitalService> specification = new SearchSpecification<>(request);
        Pageable pageable = SearchSpecification.getPageable(request.getPage(), request.getSize());
        return serviceRepository.findAll(specification, pageable);
    }

    @Override
    public Page<HospitalService> findAllByHospitalId(String hospitalId, Pageable pageable) {
        return serviceRepository.findAllByHospitalId(hospitalId, pageable);
    }
}
