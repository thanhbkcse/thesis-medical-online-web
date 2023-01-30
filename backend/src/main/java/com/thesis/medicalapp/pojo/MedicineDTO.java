package com.thesis.medicalapp.pojo;

import com.thesis.medicalapp.models.Medicine;
import com.thesis.medicalapp.models.StoreMedicine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MedicineDTO {
    private String id;
    private StoreMedicine storeMedicine;
    private String unit;
    private String numMedicineInMorning;
    private String numMedicineInAfternoon;
    private String numMedicineInEvening;
    private String dateStart;
    private String dateEnd;
}
