package com.thesis.medicalapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "doctors")
public class Doctor extends User implements Serializable {
    @Column(nullable = false)
    private String name;
    private Gender gender;
    private Date dob;
    private String email;
    private SpecialtyType specialty;
    private String level;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "hospital_id", referencedColumnName = "id")
    private Hospital hospital;
    @Type(type="text")
    private String bio;
    private Integer registrationNumber = 0;
    private Double favorite = 0.0;
    private Integer price;

    public Doctor(String id, String username, String phone, String password, Boolean enabled, String imageUrl, Collection<Role> roles, String name, Gender gender, Date dob, String email,  SpecialtyType specialty, String level, String bio, Integer price, Hospital hospital, Double favorite) {
        super(id, username, phone, password, enabled, imageUrl, roles);
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.email = email;
        this.specialty = specialty;
        this.level = level;
        this.bio = bio;
        this.price = price;
        this.hospital = hospital;
        this.favorite = favorite;
    }
}
