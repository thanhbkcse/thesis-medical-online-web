package com.thesis.medicalapp.controllers;

import com.thesis.medicalapp.exception.ApiRequestException;
import com.thesis.medicalapp.models.Room;
import com.thesis.medicalapp.models.Schedule;
import com.thesis.medicalapp.models.Time;
import com.thesis.medicalapp.payload.ListSchedule;
import com.thesis.medicalapp.payload.RoomDate;
import com.thesis.medicalapp.payload.response.ApiResponse;
import com.thesis.medicalapp.pojo.ScheduleDTO;
import com.thesis.medicalapp.repository.RoomRepository;
import com.thesis.medicalapp.repository.ScheduleRepository;
import com.thesis.medicalapp.services.ScheduleService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final RoomRepository roomRepository;
    private final ScheduleRepository scheduleRepository;

    @PostMapping("/schedules")
    public ResponseEntity<Object> saveSchedule(@RequestBody @Valid ListSchedule request) {
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for (RoomDate d : request.getList()) {
            ScheduleDTO schedule = new ScheduleDTO();
            schedule.setType(d.getType());
            schedule.setDate(d.getDate());
            schedule.setNumOfAppointmentPerHour(d.getNumOfAppointmentPerHour());
            schedule.setDoctor(request.getDoctor());
            Room room = roomRepository.findRoomById(d.getRoom().getId());
            if (room == null) throw new ApiRequestException("Could not find room!");
            schedule.setRoom(room);
            for (String time : d.getTimes()) {
                if (!Arrays.asList(Time.times).contains(time)) throw new ApiRequestException("Invalid time!");
            }
            schedule.setTimes(d.getTimes());
            ScheduleDTO scheduleDTO = scheduleService.saveSchedule(schedule);
            scheduleDTOS.add(scheduleDTO);
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(scheduleDTOS)
        );
    }
    @GetMapping("/schedules/all")
    public ResponseEntity<Object> getSchedules(@RequestBody @Valid ListSchedule request) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(scheduleService.getSchedules())
        );
    }

    @GetMapping("/schedules/doctors")
    public ResponseEntity<Object> getDoctorsBySchedule(@RequestParam("specialty") String specialty, @RequestParam("date") String date) {
        List<ScheduleDTO> scheduleDTOS = scheduleService.getDoctorsBySchedule(specialty, date);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(scheduleDTOS)
        );
    }

    @GetMapping("/schedules")
    public ResponseEntity<Object> getScheduleByDateAndDoctor(@RequestParam("date") String date, @RequestParam("doctorId") String doctorId) {
        List<Schedule> schedules = scheduleService.getSchedulesByDateAndDoctor(date, doctorId)
                .stream()
                .collect(Collectors.toList());
        List<ScheduleDTO> scheduleDTOS = schedules.stream().map(s -> {
            ScheduleDTO scheduleDTO = ScheduleDTO.from(s);
            return scheduleDTO;
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(scheduleDTOS)
        );
    }

    @PatchMapping("/schedules")
    public ResponseEntity<Integer> updateSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        int result = scheduleService.updateSchedule(scheduleDTO);
        return ResponseEntity.ok(result);
    }
    @DeleteMapping("/schedules/{id}")
    public ResponseEntity<Integer> removeSchedule(@PathVariable String id) {
        int result = scheduleService.removeSchedule(id);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/doctor/schedules")
    public ResponseEntity<ApiResponse> getSchedulesByDoctor() {
        List<ScheduleDTO> scheduleDTOS = scheduleService.getSchedulesByDoctor();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(scheduleDTOS)
        );
    }
    @GetMapping("/doctor/schedules/date")
    public ResponseEntity<ApiResponse> getDoctorsByStartDateAndEndDate(@RequestParam("dateStart") String dateStart, @RequestParam("dateEnd") String dateEnd, @RequestParam("doctorId") String doctorId) {
        Date DateStart = new Date();
        try {
            DateStart = new SimpleDateFormat("yyyy-MM-dd").parse(dateStart);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Date DateEnd = new Date();
        try {
            DateEnd = new SimpleDateFormat("yyyy-MM-dd").parse(dateEnd);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        List<ScheduleDTO> scheduleDTOS = scheduleService.getAllByDateIsBetweenAndDoctor(DateStart, DateEnd, doctorId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(scheduleDTOS)
        );
    }
}
