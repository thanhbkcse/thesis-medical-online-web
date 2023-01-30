package com.thesis.medicalapp.services.impl;

import com.thesis.medicalapp.models.StoreMedicine;
import com.thesis.medicalapp.repository.StoreMedicineRepository;
import com.thesis.medicalapp.search.SearchRequest;
import com.thesis.medicalapp.search.SearchSpecification;
import com.thesis.medicalapp.services.StoreMedicineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class StoreMedicineServiceImpl implements StoreMedicineService {
    private final StoreMedicineRepository storeMedicineRepository;

    @Override
    public StoreMedicine save(StoreMedicine storeMedicine){
        return storeMedicineRepository.save(storeMedicine);
    }

    @Override
    public List<StoreMedicine> getAll(){
        List<StoreMedicine> storeMedicines = storeMedicineRepository.findAll();
        return storeMedicines;
    }

    @Override
    public Boolean existsById(String id) {
        return storeMedicineRepository.existsById(id);
    }

    @Override
    public List<StoreMedicine> getByHospital(String hospitalId) {
        List<StoreMedicine> storeMedicines = storeMedicineRepository.findByHospitalId(hospitalId);
        return storeMedicines;
    }

    @Override
    public Page<StoreMedicine> search(SearchRequest request) {
        SearchSpecification<StoreMedicine> specification = new SearchSpecification<>(request);
        Pageable pageable = SearchSpecification.getPageable(request.getPage(), request.getSize());
        return storeMedicineRepository.findAll(specification, pageable);
    }
}
