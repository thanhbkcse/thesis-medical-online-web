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
@Document(indexName = Indices.HOSPITAL_SERVICE_INDEX)
public class HospitalServiceES {
    @Id
    private String id;
    private String name;
    private String info;
    private Integer price;
    private String imageUrl;
    private Hospital hospital;
}
