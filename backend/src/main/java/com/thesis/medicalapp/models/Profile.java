package com.thesis.medicalapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "profiles")
public class Profile extends AuditLog {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String profileNumber;
    private String firstName;
    private String lastName;
    @OneToOne
    private Address address;
    private String phone;
    private String email;
    private Date dob;
    private String job;
    private String identityCard;
    private String healthInsurance;
    private String folk;
    private Gender gender;
    private String guardian;
    private String guardianPhone;
    private String guardianIdentityCard;
    private String relationship;
    private String relationshipWithPatient;
    private String imageUrl;
    private Boolean isContactProfile;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
}
