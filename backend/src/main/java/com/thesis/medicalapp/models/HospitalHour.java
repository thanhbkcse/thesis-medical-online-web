package com.thesis.medicalapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hospitals_hours")
public class HospitalHour extends AuditLog {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String mondayTime;
    private String mondayTimeBreak;
    private String tuesdayTime;
    private String tuesdayTimeBreak;
    private String wednesdayTime;
    private String wednesdayTimeBreak;
    private String thursdayTime;
    private String thursdayTimeBreak;
    private String fridayTime;
    private String fridayTimeBreak;
    private String saturdayTime;
    private String saturdayTimeBreak;
    private String sundayTime;
    private String sundayTimeBreak;
}
