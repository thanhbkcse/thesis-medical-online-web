package com.thesis.medicalapp.controllers;

import com.thesis.medicalapp.exception.ApiRequestException;
import com.thesis.medicalapp.models.*;
import com.thesis.medicalapp.models.Record;
import com.thesis.medicalapp.payload.MedicineRequest;
import com.thesis.medicalapp.payload.RecordRequest;
import com.thesis.medicalapp.payload.response.ApiResponse;
import com.thesis.medicalapp.pojo.RecordDTO;
import com.thesis.medicalapp.pojo.UserDoctorDTO;
import com.thesis.medicalapp.repository.AppointmentRepository;
import com.thesis.medicalapp.repository.MedicalFileRepository;
import com.thesis.medicalapp.repository.MedicineRepository;
import com.thesis.medicalapp.repository.RecordRepository;
import com.thesis.medicalapp.search.SearchRequest;
import com.thesis.medicalapp.search.SearchSpecification;
import com.thesis.medicalapp.services.MedicineService;
import com.thesis.medicalapp.services.RecordService;
import com.thesis.medicalapp.utils.SequenceGenerator;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RecordController {
    private final RecordService recordService;
    private final AppointmentRepository appointmentRepository;
    private final MedicalFileRepository medicalFileRepository;
    private final MedicineService medicineService;
    private final RecordRepository recordRepository;
    private static final ModelMapper modelMapper = new ModelMapper();

    @PostMapping(value = "/records")
    public ResponseEntity<ApiResponse> saveRecord(@RequestBody @Valid RecordRequest recordRequest) {
        Record record = new Record();
        Appointment appointment = appointmentRepository.findAppointmentById(recordRequest.getAppointmentId());
        if (appointment == null) throw new ApiRequestException("Không tìm thấy lịch hẹn!");
        record.setAppointment(appointment);
        SequenceGenerator sequenceGenerator = new SequenceGenerator();
        Long recordId = sequenceGenerator.nextId();
        record.setRecordId(String.valueOf(recordId));
        record.setDiagnose(recordRequest.getDiagnose());
        record.setPrescribe(recordRequest.getPrescribe());
        record.setNote(recordRequest.getNote());
        record.setBloodVessel(recordRequest.getBloodVessel());
        record.setBloodPressure(recordRequest.getBloodPressure());
        record.setHeartbeat(recordRequest.getHeartbeat());
        record.setTemperature(recordRequest.getTemperature());
        record.setHeight(recordRequest.getHeight());
        record.setWeight(recordRequest.getWeight());
        record.setHospitalize(recordRequest.getHospitalize());
        record.setMedicines(new ArrayList<>());
        if (null != recordRequest.getMedicines()) {
            System.out.println("save medicine!");
            for (MedicineRequest medicineRequest: recordRequest.getMedicines()) {
                Medicine m = medicineService.save(medicineRequest);
                record.getMedicines().add(m);
            }
        }
        record.setFiles(new ArrayList<>());
        if (null != recordRequest.getFiles()) {
            for (MedicalFile file : recordRequest.getFiles()) {
                MedicalFile File = medicalFileRepository.save(file);
                record.getFiles().add(File);
            }
        }

        appointment.setStatus(Status.COMPLETE);
        appointment.setIsPaid(Boolean.TRUE);
        if (null != recordRequest.getReExaminationDate()) {
            Date dateFormat = new Date();
            try {
                dateFormat = new SimpleDateFormat("yyyy-MM-dd").parse(recordRequest.getReExaminationDate());
                record.setReExaminationDate(dateFormat);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        RecordDTO recordDTO = recordService.saveRecord(record);
        appointmentRepository.save(appointment);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(recordDTO)
        );
    }

    @GetMapping("/records")
    public ResponseEntity<Object> getAllRecords(
            @RequestParam Integer size,
            @RequestParam Integer page
    ) {
        SearchRequest request = new SearchRequest();
        request.setFilters(new ArrayList<>());
        request.setSorts(new ArrayList<>());
        request.setPage(page);
        request.setSize(size);
        Page<Record> records = recordService.findAll(request);
        Page<RecordDTO> recordDTOS = records.map(
                record -> {
                    RecordDTO recordDTO = RecordDTO.from(record);
                    return recordDTO;
                }
        );
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(recordDTOS.getContent(), recordDTOS)
        );
    }

    @PostMapping("/doctor/records/search")
    public ResponseEntity<Object> getAllRecordsByDoctor(
            @RequestBody SearchRequest request
    ) {
        Page<Record> records = recordService.findAll(request);
        Page<RecordDTO> recordDTOS = records.map(
                record -> {
                    RecordDTO recordDTO = RecordDTO.from(record);
                    return recordDTO;
                }
        );
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(recordDTOS.getContent(), recordDTOS)
        );
    }

    @GetMapping("/user/records")
    public ResponseEntity<ApiResponse> getRecordsByProfile(@RequestParam String profileId) {
        List<RecordDTO> recordDTOS = recordService.getRecordsByProfile(profileId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(recordDTOS)
        );
    }

    @GetMapping("/appointments/record")
    public ResponseEntity<ApiResponse> getRecordsByAppointment(@RequestParam String appointmentId) {
        RecordDTO recordDTO = recordService.getRecordByAppointment(appointmentId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(recordDTO)
        );
    }

    @PatchMapping("/records")
    public ResponseEntity<Object> updateRecord(@RequestBody RecordDTO recordDTO) {
        Record record = recordRepository.findById(recordDTO.getId()).get();
        if (recordDTO.getDiagnose() != null) record.setDiagnose(recordDTO.getDiagnose());
        if (recordDTO.getPrescribe() != null) record.setPrescribe(recordDTO.getPrescribe());
        if (recordDTO.getNote() != null) record.setNote(recordDTO.getNote());
        if (recordDTO.getBloodPressure() != null) record.setBloodPressure(recordDTO.getBloodPressure());
        if (recordDTO.getBloodVessel() != null) record.setBloodVessel(recordDTO.getBloodVessel());
        if (recordDTO.getHeartbeat() != null) record.setHeartbeat(recordDTO.getHeartbeat());
        if (recordDTO.getTemperature() != null) record.setTemperature(recordDTO.getTemperature());
        if (recordDTO.getHeight() != null) record.setHeight(recordDTO.getHeight());
        if (recordDTO.getWeight() != null) record.setWeight(recordDTO.getWeight());
        if (recordDTO.getHospitalize() != null) record.setHospitalize(recordDTO.getHospitalize());
        if (recordDTO.getReExaminationDate() != null) record.setReExaminationDate(recordDTO.getReExaminationDate());
        if (!recordDTO.getFiles().isEmpty()) {
            record.setFiles(new ArrayList<>());
            for (MedicalFile file : recordDTO.getFiles()) {
                if (file.getId() != null) {
                    record.getFiles().add(file);
                } else {
                    MedicalFile File = medicalFileRepository.save(file);
                    record.getFiles().add(File);
                }
            }
        }
        if (recordDTO.getUpdateMedicines() != null) {
            record.setMedicines(new ArrayList<>());
            for (MedicineRequest medicineRequest : recordDTO.getUpdateMedicines()) {
                Medicine m = medicineService.save(medicineRequest);
                record.getMedicines().add(m);
            }
        }
        recordRepository.save(record);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(null)
        );
    }
}
