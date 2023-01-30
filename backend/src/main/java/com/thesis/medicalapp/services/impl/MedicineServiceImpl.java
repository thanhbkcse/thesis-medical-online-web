package com.thesis.medicalapp.services.impl;

import com.thesis.medicalapp.exception.ApiRequestException;
import com.thesis.medicalapp.models.Medicine;
import com.thesis.medicalapp.models.StoreMedicine;
import com.thesis.medicalapp.payload.MedicineRequest;
import com.thesis.medicalapp.pojo.MedicineDTO;
import com.thesis.medicalapp.repository.MedicineRepository;
import com.thesis.medicalapp.repository.StoreMedicineRepository;
import com.thesis.medicalapp.services.MedicineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MedicineServiceImpl implements MedicineService {
    private final MedicineRepository medicineRepository;
    private final StoreMedicineRepository storeMedicineRepository;

    @Override
    public Medicine save(MedicineRequest medicineRequest) {
        Medicine medicine = new Medicine();
        Optional<StoreMedicine> storeMedicine = storeMedicineRepository.findById(medicineRequest.getStoreMedicineId());
        if (!storeMedicine.isPresent()) {
            throw new ApiRequestException("Không tìm thấy thuốc!");
        }
        medicine.setStoreMedicine(storeMedicine.get());
        medicine.setUnit(medicineRequest.getUnit());
        medicine.setNumMedicineInMorning(medicineRequest.getNumMedicineInMorning());
        medicine.setNumMedicineInAfternoon(medicineRequest.getNumMedicineInAfternoon());
        medicine.setNumMedicineInEvening(medicineRequest.getNumMedicineInEvening());
        Date dateStart = new Date();
        Date dateEnd = new Date();
        try {
            dateStart = new SimpleDateFormat("yyyy-MM-dd").parse(medicineRequest.getDateStart());
            dateEnd = new SimpleDateFormat("yyyy-MM-dd").parse(medicineRequest.getDateEnd());
            medicine.setDateStart(dateStart);
            medicine.setDateEnd(dateEnd);
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage());
        }
        return medicineRepository.save(medicine);
    }
    public List<Medicine> getAll() {
        return medicineRepository.findAll();
    }
}
