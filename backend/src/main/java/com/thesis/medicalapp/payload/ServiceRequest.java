package com.thesis.medicalapp.payload;

import com.thesis.medicalapp.models.ScheduleType;
import com.thesis.medicalapp.models.SpecialtyType;
import com.thesis.medicalapp.pojo.HospitalDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceRequest {
    @NotEmpty
    @NotNull
    private String name;
    private String info;
    private Integer registrationNumber;
    private Integer price;
    @NotNull
    private SpecialtyType specialty;
    @NotEmpty
    @NotNull
    private String hospitalId;
    private String hospitalName;
    private String imageUrl;
    @NotNull
    private Integer numOfServicePerHour;
    @NotNull
    private ScheduleType type;
}
