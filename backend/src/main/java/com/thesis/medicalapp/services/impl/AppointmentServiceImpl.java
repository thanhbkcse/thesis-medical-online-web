package com.thesis.medicalapp.services.impl;

import com.amazonaws.services.dynamodbv2.xspec.S;
import com.thesis.medicalapp.exception.ApiRequestException;
import com.thesis.medicalapp.models.*;
import com.thesis.medicalapp.payload.response.AppointmentsByDateAndDoctor;
import com.thesis.medicalapp.pojo.AppointmentDTO;
import com.thesis.medicalapp.pojo.UserDoctorDTO;
import com.thesis.medicalapp.repository.AppointmentRepository;
import com.thesis.medicalapp.repository.DoctorRepository;
import com.thesis.medicalapp.repository.ProfileRepository;
import com.thesis.medicalapp.services.AppointmentService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final ProfileRepository profileRepository;

    @Override
    public AppointmentDTO saveAppointment(Appointment appointment) {
        Appointment appointmentEntity = appointmentRepository.save(appointment);
        AppointmentDTO appointmentResponse = AppointmentDTO.from(appointmentEntity);
        return appointmentResponse;
    }
    @Override
    public List<AppointmentDTO> getAppointmentByProfileId(String profileId) {
        if (!profileRepository.existsById(profileId)) {
            throw new ApiRequestException("Không tìm thấy profile!");
        }
        List<Appointment> appointments = appointmentRepository.findAllByProfileId(profileId)
                .stream()
                .collect(Collectors.toList());
        List<AppointmentDTO> appointmentDTOS = appointments.stream().map(a -> {
            AppointmentDTO appointmentDTO = AppointmentDTO.from(a);
            return appointmentDTO;
        }).collect(Collectors.toList());
        return appointmentDTOS;
    }

    @Override
    public Page<AppointmentDTO> getAppointmentByUser(Pageable pageable) {
        String username = Global.user.getUsername();
        Profile profile = profileRepository.findProfileByRelationshipAndUser_Username("Chủ tài khoản", username);
        Page<Appointment> appointments = appointmentRepository.findAllByProfile_User_UsernameOrProfile_ProfileNumber(username,profile.getProfileNumber(), pageable);
        Page<AppointmentDTO> appointmentDTOS = appointments.map(
                a -> {
                    AppointmentDTO appointmentDTO = AppointmentDTO.from(a);
                    return appointmentDTO;
                }
        );
        return appointmentDTOS;
    }

    @Override
    public int updateAppointment(Appointment appointment) {
        try {
            appointmentRepository.save(appointment);
            return 1;
        } catch (Exception e) {
            System.out.println("Error in appointmet service " + e.getMessage());
            return 0;
        }
    }
    @Override
    public int removeAppointment(String id) {
        try {
            appointmentRepository.deleteById(id);
            return 1;
        } catch (Exception e) {
            System.out.println("Error in appointment service " + e.getMessage());
            return 0;
        }
    }
    @Override
    public List<AppointmentDTO> getAppointmentsByDateAndDoctor(String date) {
        Doctor doctor = doctorRepository.findDoctorByUsername(Global.user.getUsername());
        Date dateFormat = new Date();
        try {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        List<Appointment> appointments = appointmentRepository.findAllByDateAndDoctor(dateFormat, doctor)
                .stream()
                .collect(Collectors.toList());
        List<AppointmentDTO> appointmentDTOS = appointments.stream().map(a -> {
            AppointmentDTO appointmentDTO = AppointmentDTO.from(a);
            return appointmentDTO;
        }).collect(Collectors.toList());
        return appointmentDTOS;
    }
//    @Override
//    public Integer max(Date date) {
//        return appointmentRepository.max(date);
//    }
    @Override
    public Appointment findAppointmentById(String id) {
        return appointmentRepository.findAppointmentById(id);
    }
    @Override
    public List<?> getAllByDateIsBetweenAndDoctor(Date start, Date end) {
        Doctor doctor = doctorRepository.findDoctorByUsername(Global.user.getUsername());
        List<Appointment> appointments = appointmentRepository.getAllByDateIsBetweenAndDoctor(start, end, doctor);
        List<?> appointmentReturn = appointments.stream().map(a -> {
            NumberAppointment numberAppointment = new NumberAppointment(a.getId(), a.getStatus(), a.getDate());
            return numberAppointment;
        }).collect(Collectors.toList());
        return appointmentReturn;
    }
}
@Data
@AllArgsConstructor
class NumberAppointment {
    String appointmentId;
    Status status;
    Date date;
}