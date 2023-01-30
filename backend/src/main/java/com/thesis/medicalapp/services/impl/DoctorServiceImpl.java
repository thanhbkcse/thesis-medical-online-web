package com.thesis.medicalapp.services.impl;

import com.thesis.medicalapp.exception.ApiRequestException;
import com.thesis.medicalapp.models.Doctor;
import com.thesis.medicalapp.models.Hospital;
import com.thesis.medicalapp.models.HospitalService;
import com.thesis.medicalapp.models.StoreMedicine;
import com.thesis.medicalapp.pojo.DoctorDTO;
import com.thesis.medicalapp.pojo.UserDoctorDTO;
import com.thesis.medicalapp.repository.DoctorRepository;
import com.thesis.medicalapp.repository.HospitalRepository;
import com.thesis.medicalapp.repository.UserRepository;
import com.thesis.medicalapp.search.SearchRequest;
import com.thesis.medicalapp.search.SearchSpecification;
import com.thesis.medicalapp.services.DoctorService;
import com.thesis.medicalapp.services.UserService;
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
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;
    private final HospitalRepository hospitalRepository;

    @Override
    public List<UserDoctorDTO> getDoctors() {
        List<Doctor> doctors = doctorRepository.findAll()
                .stream().collect(Collectors.toList());
        List<UserDoctorDTO> doctorDTOS = doctors.stream().map(d -> {
            UserDoctorDTO doctorDTO = UserDoctorDTO.from(d);
            return doctorDTO;
        }).collect(Collectors.toList());

        return doctorDTOS;
    }
    @Override
    public Doctor findDoctorById(String id) {
        return doctorRepository.findDoctorById(id);
    }
    @Override
    public List<UserDoctorDTO> getDoctorsBySpecialty(String specialty) {
        List<Doctor> doctors = doctorRepository.findAllBySpecialty(specialty)
                .stream().collect(Collectors.toList());
        List<UserDoctorDTO> doctorDTOS = doctors.stream().map(d -> {
            UserDoctorDTO doctorDTO = UserDoctorDTO.from(d);
            return doctorDTO;
        }).collect(Collectors.toList());

        return doctorDTOS;
    }
    @Override
    public List<UserDoctorDTO> getDoctorsByHospital(String hospitalId) {
        Optional<Hospital> hospital = hospitalRepository.findById(hospitalId);
        if(!hospital.isPresent()) throw new ApiRequestException("Can not found hospital!");
        List<Doctor> doctors = doctorRepository.findAllByHospital(hospital.get())
                .stream().collect(Collectors.toList());
        List<UserDoctorDTO> doctorDTOS = doctors.stream().map(d -> {
            UserDoctorDTO doctorDTO = UserDoctorDTO.from(d);
            return doctorDTO;
        }).collect(Collectors.toList());
        return doctorDTOS;
    }

    @Override
    public Page<Doctor> search(SearchRequest request) {
        SearchSpecification<Doctor> specification = new SearchSpecification<>(request);
        Pageable pageable = SearchSpecification.getPageable(request.getPage(), request.getSize());
        return doctorRepository.findAll(specification, pageable);
    }

    @Override
    public Page<Doctor> findAllByHospitalId(String hospitalId, Pageable pageable) {
        return doctorRepository.findAllByHospitalId(hospitalId, pageable);
    }
}
