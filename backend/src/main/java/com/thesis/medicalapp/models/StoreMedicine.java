package com.thesis.medicalapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "store_medicines")
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class StoreMedicine extends AbstractProduct implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;
}
