package com.thesis.medicalapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "services")
public class HospitalService extends AbstractProduct {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private SpecialtyType specialty;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;
    private Integer numOfServicePerHour;
    private ScheduleType type;
}
