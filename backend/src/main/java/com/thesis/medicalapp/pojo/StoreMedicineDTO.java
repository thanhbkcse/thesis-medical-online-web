package com.thesis.medicalapp.pojo;

import com.thesis.medicalapp.models.StoreMedicine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class StoreMedicineDTO {
    private String id;
    private String name;
    private String imageUrl;
    private String price;
    private Integer registrationNumber;
    private String info;
    private Double favorite;
    private HospitalDTO hospital;
}
