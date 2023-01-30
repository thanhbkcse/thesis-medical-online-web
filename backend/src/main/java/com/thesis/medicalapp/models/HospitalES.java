package com.thesis.medicalapp.models;

import com.thesis.medicalapp.indices.Indices;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = Indices.HOSPITAL_INDEX)
public class HospitalES {
    @Id
    private String id;
    private String name;
    private Address address;
    private String imageUrl;
}
