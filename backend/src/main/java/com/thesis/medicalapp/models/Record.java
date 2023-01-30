package com.thesis.medicalapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "records")
public class Record extends AuditLog {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @OneToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;
    private String recordId;
    @Type(type="text")
    private String diagnose;
    @Type(type="text")
    private String prescribe;
    @Type(type="text")
    private String note;
    private String bloodVessel;
    private String temperature;
    private String bloodPressure;
    private String heartbeat;
    private Boolean hospitalize;
    private String height;
    private String weight;
    @OneToMany
    private Collection<Medicine> medicines;
    private Date reExaminationDate;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(value= FetchMode.SELECT)
    private Collection<MedicalFile> files;
}
