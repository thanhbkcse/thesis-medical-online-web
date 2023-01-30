package com.thesis.medicalapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hospitals")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Hospital extends AbstractFavorite implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @OneToOne
    private Address address;
    @Type(type="text")
    private String mapImageUrl;
    private Boolean isActive;
    @OneToOne
    private HospitalHour hospitalHour;

    public Hospital(String id) {
        this.id = id;
    }
}
