package com.thesis.medicalapp.services;

import com.thesis.medicalapp.models.Doctor;
import com.thesis.medicalapp.models.Profile;
import com.thesis.medicalapp.models.Schedule;
import com.thesis.medicalapp.pojo.ScheduleDTO;

import java.util.Date;
import java.util.List;

public interface ScheduleService {
    ScheduleDTO saveSchedule(ScheduleDTO scheduleDTO);
    List<ScheduleDTO> getSchedules();
    List<ScheduleDTO> getDoctorsBySchedule(String specialty, String date);
    int updateSchedule(ScheduleDTO scheduleDTO);
    int removeSchedule(String id);
    List<ScheduleDTO> getSchedulesByDoctor();
    ScheduleDTO getScheduleByDateAndDoctor(Date data, Doctor doctor);
    List<ScheduleDTO> getAllByDateIsBetweenAndDoctor(Date dateStart, Date dateEnd, String doctorId);
    List<Schedule> getSchedulesByDateAndDoctor(String date, String doctorId);
}
