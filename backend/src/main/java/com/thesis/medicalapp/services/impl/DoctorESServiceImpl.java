package com.thesis.medicalapp.services.impl;

import com.thesis.medicalapp.models.Doctor;
import com.thesis.medicalapp.models.DoctorES;
import com.thesis.medicalapp.models.User;
import com.thesis.medicalapp.repository.DoctorESRepository;
import com.thesis.medicalapp.services.DoctorESService;
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
public class DoctorESServiceImpl implements DoctorESService {
    private final DoctorESRepository doctorESRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    @Async
    public DoctorES save(Doctor doctor) {
        DoctorES doctorES = modelMapper.map(doctor, DoctorES.class);
        doctorES.setSpecialty(doctor.getSpecialty().getName());
        return doctorESRepository.save(doctorES);
    }

    @Override
    @Async
    public void delete(String id) {
        doctorESRepository.deleteById(id);
    }

    @Override
    @Async
    public void deleteAllByHospitalId(String id) {
        doctorESRepository.deleteAllByHospitalId(id);
    }

    @Override
    public Page<DoctorES> search(String name) {
        return doctorESRepository.findByName(name, PageRequest.of(0, 8));
    }
}
