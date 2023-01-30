package com.thesis.medicalapp.pojo;

import com.thesis.medicalapp.models.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDTO {
    private String id;
    private Long code;
    @NotNull
    private Room room;
    @NotNull
    private ProfileDTO profile;
    private DoctorDTO doctor;
    private HospitalService service;
    @NotNull
    private Date date;
    @NotEmpty
    @NotBlank
    private String time;
    @NotEmpty
    @NotBlank
    private String symptom;
    private Collection<MedicalFile> files;
    private Status status;
    @NotNull
    private ScheduleType type;
    private Boolean isPaid;
    private Integer fee;
    private CategoryType category;
    private String orderId;
    private String qrcode;
    private PaymentType paymentType;
    private Date createdAt;

    public static AppointmentDTO from(Appointment appointment) {
        DoctorDTO doctorDTO;
        if (appointment.getCategory().equals(CategoryType.DOCTOR)) {
            doctorDTO = DoctorDTO.from(appointment.getDoctor());
        } else doctorDTO = null;
        return AppointmentDTO.builder()
                .id(appointment.getId())
                .code(appointment.getCode())
                .room(appointment.getRoom())
                .profile(ProfileDTO.from(appointment.getProfile()))
                .doctor(doctorDTO)
                .service(appointment.getService())
                .date(appointment.getDate())
                .time(appointment.getTime())
                .symptom(appointment.getSymptom())
                .files(appointment.getFiles())
                .status(appointment.getStatus())
                .type(appointment.getType())
                .isPaid(appointment.getIsPaid())
                .fee(appointment.getFee())
                .category(appointment.getCategory())
                .orderId(appointment.getOrderId())
                .qrcode(appointment.getQrcode())
                .paymentType(appointment.getPaymentType())
                .createdAt(appointment.getCreatedAt())
                .build();
    }
}
