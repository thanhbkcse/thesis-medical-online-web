package com.thesis.medicalapp.services;

import com.thesis.medicalapp.models.StoreMedicine;
import com.thesis.medicalapp.search.SearchRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StoreMedicineService {
    StoreMedicine save(StoreMedicine storeMedicine);
    List<StoreMedicine> getAll();
    List<StoreMedicine> getByHospital(String hospitalId);
    Boolean existsById(String id);
    Page<StoreMedicine> search(SearchRequest request);
}
