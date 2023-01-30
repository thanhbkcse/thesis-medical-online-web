package com.thesis.medicalapp.pojo;

import com.thesis.medicalapp.models.Room;
import com.thesis.medicalapp.models.Schedule;
import com.thesis.medicalapp.models.ScheduleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDTO {
    private String id;
    @NotNull
    private ScheduleType type;
    private Date date;
    private Room room;
    private List<String> times;
    private Integer numOfAppointmentPerHour;
    private DoctorDTO doctor;

    public static ScheduleDTO from(Schedule schedule) {
        return ScheduleDTO.builder()
                .id(schedule.getId())
                .type(schedule.getType())
                .date(schedule.getDate())
                .room(schedule.getRoom())
                .times(schedule.getTimes())
                .numOfAppointmentPerHour(schedule.getNumOfAppointmentPerHour())
                .doctor(DoctorDTO.from(schedule.getDoctor()))
                .build();
    }
}
