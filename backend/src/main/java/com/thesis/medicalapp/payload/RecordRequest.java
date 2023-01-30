package com.thesis.medicalapp.payload;

import com.thesis.medicalapp.models.MedicalFile;
import com.thesis.medicalapp.models.Medicine;
import com.thesis.medicalapp.pojo.AppointmentDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecordRequest {
    @NotEmpty
    @NotBlank
    private String appointmentId;
    private String diagnose;
    private String prescribe;
    private String note;
    private String bloodVessel;
    private String temperature;
    private String bloodPressure;
    private String heartbeat;
    private Boolean hospitalize;
    private String height;
    private String weight;
    private Collection<@Valid MedicineRequest> medicines;
    private String reExaminationDate;
    private Collection<MedicalFile> files;
}