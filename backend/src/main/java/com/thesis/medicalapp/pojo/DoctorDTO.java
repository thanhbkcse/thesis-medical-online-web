package com.thesis.medicalapp.pojo;

import com.thesis.medicalapp.models.Doctor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDTO {
    private String id;
    private String name;
    private String email;
    private String specialty;
    private HospitalDTO hospital;
    private String level;
    private String bio;
    private Integer registrationNumber;
    private Integer price;
    private String imageUrl;
    private Double favorite;

    public static DoctorDTO from(Doctor doctor){
        return DoctorDTO.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .email(doctor.getEmail())
                .specialty(doctor.getSpecialty().getName())
                .hospital(HospitalDTO.from(doctor.getHospital()))
                .level(doctor.getLevel())
                .bio(doctor.getBio())
                .registrationNumber(doctor.getRegistrationNumber())
                .price(doctor.getPrice())
                .imageUrl(doctor.getImageUrl())
                .favorite(doctor.getFavorite())
                .build();
    }
}
