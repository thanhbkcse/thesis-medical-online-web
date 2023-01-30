package com.thesis.medicalapp.payload;

import com.thesis.medicalapp.models.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRequest {
    private String id;
    @NotEmpty
    @NotBlank
    private String profileId;
    private String doctorId;
    private String serviceId;
    @NotEmpty
    @NotBlank
    private String date;
    private String roomId;
    private String symptom;
    @NotEmpty
    @NotBlank
    private String time;
    @NotNull
    private ScheduleType type;
    private List<MedicalFile> files;
    private Integer fee;
    @NotNull
    private Boolean isPaid;
    @NotNull
    private CategoryType category;
    private String orderId;
    private String requestId;
    private PaymentType paymentType;
    private Status status;
}
