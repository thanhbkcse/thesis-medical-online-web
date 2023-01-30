package com.thesis.medicalapp.pojo;

import com.thesis.medicalapp.models.*;
import com.thesis.medicalapp.models.Record;
import com.thesis.medicalapp.payload.MedicineRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Collection;
import java.util.Date;
import java.util.List;


@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RecordDTO {
    private String id;
    private String recordId;
    private AppointmentDTO appointment;
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
    private Collection<Medicine> medicines;
    private Collection<MedicineRequest> updateMedicines;
    private Date reExaminationDate;
    private Collection<MedicalFile> files;
    private Date createdAt;

    public static RecordDTO from(Record record) {
        return RecordDTO.builder()
                .id(record.getId())
                .recordId(record.getRecordId())
                .appointment(AppointmentDTO.from(record.getAppointment()))
                .diagnose(record.getDiagnose())
                .prescribe(record.getPrescribe())
                .note(record.getNote())
                .bloodVessel(record.getBloodVessel())
                .temperature(record.getTemperature())
                .bloodPressure(record.getBloodPressure())
                .heartbeat(record.getHeartbeat())
                .hospitalize(record.getHospitalize())
                .height(record.getHeight())
                .weight(record.getWeight())
                .medicines(record.getMedicines())
                .reExaminationDate(record.getReExaminationDate())
                .files(record.getFiles())
                .createdAt(record.getCreatedAt())
                .build();
    }
}
