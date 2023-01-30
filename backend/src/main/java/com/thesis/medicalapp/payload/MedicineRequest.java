package com.thesis.medicalapp.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class MedicineRequest {
    @NotEmpty
    @NotBlank
    private String storeMedicineId;
    @NotEmpty
    @NotBlank
    private String unit;
    private Integer numMedicineInMorning;
    private Integer numMedicineInAfternoon;
    private Integer numMedicineInEvening;
    @NotEmpty
    @NotBlank
    private String dateStart;
    @NotEmpty
    @NotBlank
    private String dateEnd;
}
