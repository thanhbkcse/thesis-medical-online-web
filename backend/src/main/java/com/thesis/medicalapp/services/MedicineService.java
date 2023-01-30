package com.thesis.medicalapp.services;

import com.thesis.medicalapp.models.Medicine;
import com.thesis.medicalapp.payload.MedicineRequest;
import com.thesis.medicalapp.pojo.MedicineDTO;

import java.util.List;

public interface MedicineService {
    Medicine save(MedicineRequest medicine);
    List<Medicine> getAll();
}
