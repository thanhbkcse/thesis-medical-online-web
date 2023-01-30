package com.thesis.medicalapp.payload;

import com.thesis.medicalapp.models.Doctor;
import com.thesis.medicalapp.pojo.DoctorDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListSchedule {
    @NotNull
    private DoctorDTO doctor;
    @Valid
    @NotNull
    private List<RoomDate> list;
}