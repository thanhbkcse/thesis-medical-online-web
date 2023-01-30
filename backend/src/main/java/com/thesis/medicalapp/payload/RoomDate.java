package com.thesis.medicalapp.payload;

import com.thesis.medicalapp.models.Room;
import com.thesis.medicalapp.models.ScheduleType;
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
public class RoomDate {
    @NotNull
    private ScheduleType type;
    @NotNull
    private Date date;
    @NotNull
    private List<String> times;
    @NotNull
    private Room room;
    @NotNull
    private Integer numOfAppointmentPerHour;
}
