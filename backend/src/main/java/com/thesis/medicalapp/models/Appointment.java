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
@Table(name = "appointments")
public class Appointment extends AuditLog {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private Long code;
    @OneToOne
    private Room room;
    @Temporal(TemporalType.DATE)
    private Date date;
    private String time;
    private String symptom;
    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    @ManyToOne
    @JoinColumn(name = "service_id")
    private HospitalService service;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(value= FetchMode.SELECT)
    private Collection<MedicalFile> files;
    private Status status;
    @Column(nullable = false)
    private ScheduleType type;
    @Column(nullable = false)
    private Boolean isPaid = false;
    private Integer fee;
    private CategoryType category;
    private String orderId;
    private String requestId;
    @Type(type="text")
    private String qrcode;
    private PaymentType paymentType;
}
