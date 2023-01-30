package com.thesis.medicalapp.payload.response;

import com.thesis.medicalapp.pojo.AppointmentDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AppointmentsByDateAndDoctor {
    private String time;
    private List<AppointmentDTO> appointments;
}
