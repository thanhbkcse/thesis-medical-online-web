package com.thesis.medicalapp.models;

import com.thesis.medicalapp.indices.Indices;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = Indices.DOCTOR_INDEX)
public class DoctorES {
    @Id
    private String id;
    private String name;
    private String imageUrl;
    private String level;
    private Integer price;
    private String specialty;
    private Hospital hospital;
}
