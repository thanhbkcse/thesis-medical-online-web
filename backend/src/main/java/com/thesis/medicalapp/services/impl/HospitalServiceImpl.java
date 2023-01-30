package com.thesis.medicalapp.services.impl;

import com.thesis.medicalapp.exception.ApiRequestException;
import com.thesis.medicalapp.models.Address;
import com.thesis.medicalapp.models.Hospital;
import com.thesis.medicalapp.models.HospitalHour;
import com.thesis.medicalapp.models.StoreMedicine;
import com.thesis.medicalapp.pojo.HospitalDTO;
import com.thesis.medicalapp.repository.AddressRepository;
import com.thesis.medicalapp.repository.DoctorESRepository;
import com.thesis.medicalapp.repository.HospitalHourRepository;
import com.thesis.medicalapp.repository.HospitalRepository;
import com.thesis.medicalapp.search.SearchRequest;
import com.thesis.medicalapp.search.SearchSpecification;
import com.thesis.medicalapp.services.DoctorESService;
import com.thesis.medicalapp.services.HospitalService;
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
public class HospitalServiceImpl implements HospitalService {
    private final HospitalRepository hospitalRepository;
    private final AddressRepository addressRepository;
    private final HospitalHourRepository hospitalHourRepository;

    @Override
    public Hospital saveHospital(HospitalDTO hospitalDTO) {
        log.info("Saving new hospital {} to the database", hospitalDTO.getName());
        Hospital hospital = new Hospital();
        hospital.setName(hospitalDTO.getName());
        if (hospitalDTO.getAddress() == null) throw new ApiRequestException("Địa chỉ không hợp lệ!");
        Address address = addressRepository.save(hospitalDTO.getAddress());
        hospital.setAddress(address);
        hospital.setInfo(hospitalDTO.getInfo());
        hospital.setRegistrationNumber(0);
        hospital.setFavorite(0.0);
        hospital.setMapImageUrl(hospitalDTO.getMapImageUrl());
        hospital.setImageUrl(hospitalDTO.getImageUrl());
        hospital.setIsActive(true);
        if (hospitalDTO.getHospitalHour() == null) throw new ApiRequestException("Giờ làm việc không hợp lệ!");
        HospitalHour hospitalHour = hospitalHourRepository.save(hospitalDTO.getHospitalHour());
        hospital.setHospitalHour(hospitalHour);
        Hospital hospitalRes = hospitalRepository.save(hospital);
        return hospitalRes;
    }

    @Override
    public Hospital update(Hospital hospital) {
        if (!hospitalRepository.existsById(hospital.getId()))
            throw new ApiRequestException("Không tìm thấy bệnh viện!");
        return hospitalRepository.save(hospital);
    }

    @Override
    public void delete(String id) {
        if (!hospitalRepository.existsById(id))
            throw new ApiRequestException("Không tìm thấy bệnh viện!");
        hospitalRepository.deleteById(id);
    }

    @Override
    public List<HospitalDTO> getHospitals() {
        List<Hospital> hospitals = hospitalRepository.findAll()
                .stream().collect(Collectors.toList());
        List<HospitalDTO> hospitalDTOS = hospitals.stream().map(d -> {
            HospitalDTO hospitalDTO = HospitalDTO.from(d);
            return hospitalDTO;
        }).collect(Collectors.toList());
        return hospitalDTOS;
    }
    @Override
    public HospitalDTO getHospitalById(String id) {
        Optional<Hospital> hospitalOp = hospitalRepository.findById(id);
        Hospital hospital = hospitalOp.get();
        return HospitalDTO.from(hospital);
    }

    @Override
    public Boolean existsById(String id) {
        return hospitalRepository.existsById(id);
    }

    @Override
    public Page<Hospital> search(SearchRequest request) {
        SearchSpecification<Hospital> specification = new SearchSpecification<>(request);
        Pageable pageable = SearchSpecification.getPageable(request.getPage(), request.getSize());
        return hospitalRepository.findAll(specification, pageable);
    }
}
